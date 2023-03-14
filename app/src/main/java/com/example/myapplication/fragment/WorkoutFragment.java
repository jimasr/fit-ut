package com.example.myapplication.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.api.APIClient;
import com.example.myapplication.api.APIInterface;
import com.example.myapplication.pojo.MultipleResource;
import com.example.myapplication.util.WorkoutAdapter;
import com.example.myapplication.util.WorkoutItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutFragment extends Fragment {

    private static final String TAG = "WorkoutActivity";
    APIInterface apiInterface;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_workout, container, false);

        RecyclerView recyclerView = result.findViewById(R.id.workout_recyclerview);

        // TEST DATA

        List<WorkoutItem> items = new ArrayList<WorkoutItem>();

        items.add(new WorkoutItem("Cardio", "10", R.drawable.cardio));
        items.add(new WorkoutItem("Strength", "15", R.drawable.strength));
        items.add(new WorkoutItem("Stretching", "2", R.drawable.stretching));

        /////////////////////////////////////

        apiInterface = APIClient.getClient().create(APIInterface.class);

        // TEST RETROFIT
        /**
         GET List Resources
         **/
        /*
        Call<MultipleResource> call = apiInterface.doGetListResources();
        call.enqueue(new Callback<MultipleResource>() {
            @Override
            public void onResponse(Call<MultipleResource> call, Response<MultipleResource> response) {

                Log.d("TAG",response.code()+"");

                String displayResponse = "";

                MultipleResource resource = response.body();
                Integer text = resource.page;
                Integer total = resource.total;
                Integer totalPages = resource.totalPages;
                List<MultipleResource.Datum> datumList = resource.data;

                displayResponse += text + " Page\n" + total + " Total\n" + totalPages + " Total Pages\n";

                for (MultipleResource.Datum datum : datumList) {
                    displayResponse += datum.id + " " + datum.name + " " + datum.pantoneValue + " " + datum.year + "\n";
                }

                //responseText.setText(displayResponse);

            }

            @Override
            public void onFailure(Call<MultipleResource> call, Throwable t) {
                call.cancel();
            }
        });

        /**
         Create new workout
         **/
        WorkoutItem workout = new WorkoutItem("Dexterity", "10", R.drawable.strength);
        Call<WorkoutItem> call1 = apiInterface.createWorkout(workout);
        call1.enqueue(new Callback<WorkoutItem>() {
            @Override
            public void onResponse(Call<WorkoutItem> call, Response<WorkoutItem> response) {
                WorkoutItem workout1 = response.body();

                Toast.makeText(result.getContext(), workout1.getType_name(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<WorkoutItem> call, Throwable t) {
                call.cancel();
            }
        });


        /////////////////////////////////////

        recyclerView.setLayoutManager(new LinearLayoutManager(result.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new WorkoutAdapter(result.getContext(), items));

        return result;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public WorkoutFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WorkoutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WorkoutFragment newInstance(String param1, String param2) {
        WorkoutFragment fragment = new WorkoutFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
}