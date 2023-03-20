package com.example.myapplication.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.R;
import com.example.myapplication.api.APIClient;
import com.example.myapplication.api.APIInterface;
import com.example.myapplication.api.MapsAPI;
import com.example.myapplication.entity.Place;
import com.example.myapplication.pojo.maps.Geometry;
import com.example.myapplication.pojo.maps.MapsPojo;
import com.example.myapplication.pojo.maps.Result;
import com.example.myapplication.util.AppStatus;
import com.example.myapplication.util.FetchData;
import com.example.myapplication.util.StorageManager;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements OnMapReadyCallback {
    private static final String BASE_URL = "https://maps.googleapis.com";
    private static final String MAPVIEW_BUNDLE_KEY = "MapViewBundleKey";
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private MapView mapView;
    private LocationRequest locationRequest;
    private Location lastLocation;
    private Marker currentLocation;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private List<Place> placeList;
    private CardView cardView;
    private TextView profileTextView;
    private ImageView profileImage;
    private StorageManager storageManager;
    private MapsAPI mapsAPI;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        storageManager = StorageManager.getInstance();
        mapsAPI = APIClient.getClient(BASE_URL).create(MapsAPI.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        mapView = view.findViewById(R.id.mapView);
        cardView = view.findViewById(R.id.cardView);
        profileTextView = view.findViewById(R.id.profileTextView);
        profileImage = view.findViewById(R.id.profileImage);

        if(AppStatus.getInstance(getContext()).isConnected()) {
            updateUI();
            initGoogleMap(savedInstanceState);
            initProfile();
        }

        return view;
    }

    private void initProfile() {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsFragment settingsFragment = new SettingsFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.relativelayout, settingsFragment)
                        .commit();
            }
        });

    }

    private void initGoogleMap(Bundle savedInstanceState){
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY);
        if (mapViewBundle == null) {
            mapViewBundle = new Bundle();
            outState.putBundle(MAPVIEW_BUNDLE_KEY, mapViewBundle);
        }

        mapView.onSaveInstanceState(mapViewBundle);
    }
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        this.googleMap = googleMap;

        locationRequest = new LocationRequest();
        locationRequest.setInterval(120000);
        locationRequest.setFastestInterval(120000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        Places.initialize(getContext(), BuildConfig.MAPS_API_KEY);

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                this.googleMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        }
        else {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
            this.googleMap.setMyLocationEnabled(true);
        }
        this.googleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            List<Location> locationList = locationResult.getLocations();
            if (locationList.size() > 0) {
                //The last location in the list is the newest
                Location location = locationList.get(locationList.size() - 1);
                lastLocation = location;
                if (currentLocation != null) {
                    currentLocation.remove();
                }

                //Place current location marker
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


                //Location marker
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title("Current Position");
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                currentLocation = googleMap.addMarker(markerOptions);

                //Gym marker
                fetchPlaces(latLng);

                //move map camera
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 11));
            }
        }
    };

    @Override
    public void onPause() {
        if(AppStatus.getInstance(getContext()).isConnected()) mapView.onPause();

        if(fusedLocationProviderClient != null) {
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if(AppStatus.getInstance(getContext()).isConnected()) mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        if(AppStatus.getInstance(getContext()).isConnected()) mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onResume() {
        if(AppStatus.getInstance(getContext()).isConnected()) mapView.onResume();
        super.onResume();
    }

    @Override
    public void onStart() {
        if(AppStatus.getInstance(getContext()).isConnected()) mapView.onStart();
        super.onStart();
    }

    @Override
    public void onStop() {
        if(AppStatus.getInstance(getContext()).isConnected()) mapView.onStop();
        super.onStop();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(getContext())
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION );
                            }
                        })
                        .create()
                        .show();


            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION );
            }
        }
    }

    /**
     * Fetch nearby gym using Retrofit
     * @param latLng Latitude and longitude origin
     */
    private void fetchPlaces(LatLng latLng) {
        String latitude = String.valueOf(latLng.latitude);
        String longitude = String.valueOf(latLng.longitude);
        placeList = new ArrayList<>();

        String location = latitude + "," + longitude;
        String type = "gym";
        String keyword = "gym";

        Call<MapsPojo> call = mapsAPI.getPlaces(location, 1000, type, keyword, BuildConfig.MAPS_API_KEY);

        call.enqueue(new Callback<MapsPojo>() {
            @Override
            public void onResponse(Call<MapsPojo> call, Response<MapsPojo> response) {
                if(response.code() == 200) {
                    Log.i("HomeFragment", "Api call success");

                    MapsPojo mapsPojo = response.body();
                    List<Result> results = mapsPojo.results;

                    Log.i("HomeFragment", results.toString());

                    for(Result result : results) {
                        String name = result.name;
                        String vicinity = result.vicinity;
                        Double lat = result.geometry.location.lat;
                        Double lng = result.geometry.location.lng;

                        Place place = new Place(name, vicinity, new LatLng(lat, lng));
                        Log.i("HomeFragment", name);
                        placeList.add(place);
                    }

                    setPlacesMarker();
                }
            }

            @Override
            public void onFailure(Call<MapsPojo> call, Throwable t) {
                Log.e("HomeFragment", "Nearby Places API fail");
            }
        });

    }

    private void setPlacesMarker() {
        for(Place place : placeList) {
            MarkerOptions marker = new MarkerOptions();
            marker.position(place.getLatLng());
            marker.title(place.getName());
            marker.icon(BitmapDescriptorFactory.defaultMarker());
            googleMap.addMarker(marker);
        }
    }


    private void updateUI() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();

            profileTextView.setText("Hi " + name);
            storageManager.retrieveData(user, profileImage, getContext());

        }
    }

}