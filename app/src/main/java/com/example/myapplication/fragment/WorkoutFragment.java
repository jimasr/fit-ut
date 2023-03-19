package com.example.myapplication.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.api.APIClient;
import com.example.myapplication.api.APIInterface;
import com.example.myapplication.entity.Exercise;
import com.example.myapplication.util.FragmentChangeListener;
import com.example.myapplication.util.WorkoutAdapter;
import com.example.myapplication.util.WorkoutItem;
import com.example.myapplication.util.WorkoutItemClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WorkoutFragment extends Fragment implements FragmentChangeListener {

    private static final String TAG = "WorkoutActivity";
    private static RecyclerView recyclerView;
    private static WorkoutAdapter workoutAdapter;
    private static final String BASE_URL = "https://api.api-ninjas.com/";
    private static APIInterface apiInterface;
    private Call<List<Exercise>> listWorkoutCall;
    private HashMap<String, String> LIST_OF_TYPE;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_workout, container, false);

        List<WorkoutItem> items = new ArrayList<WorkoutItem>();
        LIST_OF_TYPE = initWorkoutList();
        recyclerView = result.findViewById(R.id.workout_recyclerview);
        workoutAdapter = new WorkoutAdapter(result.getContext(), items);

        recyclerView.setLayoutManager(new LinearLayoutManager(result.getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(workoutAdapter);

        // Retrofit for fetching the data from the API Ninja
        apiInterface = APIClient.getClient(BASE_URL).create(APIInterface.class);

        for (String type: LIST_OF_TYPE.keySet()) {
            WorkoutItem item = new WorkoutItem(type, 0);
            items.add(item);

            // Calling the API
            listWorkoutCall = apiInterface.getDataByType(LIST_OF_TYPE.get(type));
            listWorkoutCall.enqueue(new Callback<List<Exercise>>() {
                @Override
                public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {

                    // Checking for Response
                    if (response.code() == 200) { // HTTP Response OK

                        List<Exercise> data = response.body();
                        item.setNumberOfExercise(data.size());
                        workoutAdapter.notifyDataSetChanged();

                    } else {
                        Toast.makeText(result.getContext(), "\"Unable to collect the data... Please Check your connection\"", Toast.LENGTH_LONG).show();
                        return;
                    }

                }
                @Override
                public void onFailure(Call<List<Exercise>> call, Throwable t) {
                    Toast.makeText(result.getContext(), "API Failed !", Toast.LENGTH_SHORT).show();
                }
            });
        }

        configureOnClickRecyclerView();

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

    /**
     * Use this method to initialize the list of workout types
     * by using a HashMap structure with the title of the workout as key
     * and the parameter of this workout for the API call as value
     *
     * @return A HashMap of possible type of Workout
     */
    public HashMap<String, String> initWorkoutList() {
        HashMap<String, String> workoutList = new HashMap<String, String>();
        workoutList.put("Cardio", "cardio");
        workoutList.put("Olympic Weight Lifting", "olympic_weightlifting");
        workoutList.put("Plyometrics", "plyometrics");
        workoutList.put("Power Lifting", "powerlifting");
        workoutList.put("Strenght", "strength");
        workoutList.put("Stretching", "stretching");
        workoutList.put("Strongman",  "strongman");

        return workoutList;
    }


    private void configureOnClickRecyclerView(){
        WorkoutItemClickListener.addTo(recyclerView, R.layout.fragment_workout)
                .setOnItemClickListener(new WorkoutItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        WorkoutItem workoutItem = workoutAdapter.getWorkoutItem(position);
                        Toast.makeText(getContext(),"You clicked on " + workoutItem.getTypeName(),Toast.LENGTH_SHORT).show();

                        replaceFragment(new ExerciseFragment());
                    }
                });
    }

    @Override
    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.relativelayout, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.commit();
    }
}