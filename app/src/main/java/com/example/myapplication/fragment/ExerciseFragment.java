package com.example.myapplication.fragment;

import android.graphics.Paint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.api.APIClient;
import com.example.myapplication.api.NinjaAPI;
import com.example.myapplication.entity.Exercise;
import com.example.myapplication.util.ExerciseAdapter;
import com.example.myapplication.util.FragmentChangeListener;
import com.example.myapplication.util.WorkoutItem;
import com.example.myapplication.util.WorkoutItemClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseFragment extends Fragment implements FragmentChangeListener {

    private static View result;
    private static RecyclerView recyclerView;
    private static ExerciseAdapter exerciseAdapter;
    private static WorkoutItem workoutItem;
    private static final String BASE_URL = "https://api.api-ninjas.com/";
    private static NinjaAPI ninjaApi;
    private Call<List<Exercise>> listExerciseCall;
    private List<Exercise> items;

    private ImageButton previousButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        result = inflater.inflate(R.layout.fragment_exercise, container, false);

        ImageView exerciseImage = result.findViewById(R.id.exercise_image);
        TextView exercisePageTitle = result.findViewById(R.id.exercise_page_title);
        TextView exerciseNumber = result.findViewById(R.id.exercise_number_page);
        exerciseNumber.setPaintFlags(exerciseNumber.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG); //Underline

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            workoutItem = bundle.getParcelable("Workout");

            exerciseImage.setImageResource(workoutItem.getImage());
            exercisePageTitle.setText(workoutItem.getTypeName());

            items = new ArrayList<Exercise>();

            recyclerView = result.findViewById(R.id.exercise_recyclerview);
            exerciseAdapter = new ExerciseAdapter(result.getContext(), items);

            recyclerView.setLayoutManager(new LinearLayoutManager(result.getContext()));
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(exerciseAdapter);

            // Retrofit for fetching the data from the API Ninja
            ninjaApi = APIClient.getClient(BASE_URL).create(NinjaAPI.class);

            // Calling the API
            listExerciseCall = ninjaApi.getDataByType(workoutItem.getApiParam());
            listExerciseCall.enqueue(new Callback<List<Exercise>>() {
                @Override
                public void onResponse(Call<List<Exercise>> call, Response<List<Exercise>> response) {

                    // Checking for Response
                    if (response.code() == 200) { // HTTP Response OK

                        List<Exercise> data = response.body();

                        for (Exercise exercise : data) {
                            items.add(exercise);
                        }
                        exerciseNumber.setText(data.size() + " exercises");
                        exerciseAdapter.notifyDataSetChanged();

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
            configureOnClickRecyclerView();
        }

        previousButton = result.findViewById(R.id.previous_button_exercise);
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                WorkoutFragment workoutFragment = new WorkoutFragment();

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.relativelayout, workoutFragment)
                        .commit();
            }
        });

        return result;
    }

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ExerciseFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExerciseFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseFragment newInstance(String param1, String param2) {
        ExerciseFragment fragment = new ExerciseFragment();
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

    private void configureOnClickRecyclerView(){
        WorkoutItemClickListener.addTo(recyclerView, R.layout.fragment_workout)
                .setOnItemClickListener(new WorkoutItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                        Exercise exerciseItem = exerciseAdapter.getExerciseItem(position);

                        Bundle bundle = new Bundle();
                        bundle.putParcelable("Exercise", exerciseItem);
                        bundle.putParcelable("ActiveWorkout", workoutItem);

                        DetailFragment detailFragment = new DetailFragment();
                        detailFragment.setArguments(bundle);

                        replaceFragment(detailFragment);
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