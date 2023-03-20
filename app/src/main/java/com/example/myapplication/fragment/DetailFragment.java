package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Exercise;
import com.example.myapplication.util.WorkoutItem;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private static View result;
    private ImageButton previousButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        result = inflater.inflate(R.layout.fragment_detail, container, false);

        previousButton = result.findViewById(R.id.exercise_previous_button);

        //Getting TextView to change
        TextView exerciseNameTV = result.findViewById(R.id.exercise_name);
        TextView exerciseTypeTV = result.findViewById(R.id.exercise_type);
        TextView exerciseMuscleTV = result.findViewById(R.id.exercise_muscle);
        TextView exerciseEquipmentTV = result.findViewById(R.id.exercise_equipment);
        TextView exerciseDifficultyTV = result.findViewById(R.id.exercise_difficulty);
        TextView exerciseInstructionsTV = result.findViewById(R.id.exercise_instructions);

        //Getting the chosen exercise
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            Exercise exercise = bundle.getParcelable("Exercise");

            //Setting correctly the text according to the given exercise
            exerciseNameTV.setText(exercise.getName());
            exerciseTypeTV.setText(exercise.getType());
            exerciseMuscleTV.setText(exercise.getMuscle());
            exerciseEquipmentTV.setText(exercise.getEquipment());
            exerciseDifficultyTV.setText(exercise.getDifficulty());
            exerciseInstructionsTV.setText(exercise.getInstructions());
        }

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //WorkoutItem workoutItem = new WorkoutItem();
                //Bundle bundle = new Bundle();
                //bundle.putParcelable("Workout",workoutItem);

                //ExerciseFragment exerciseFragment = new ExerciseFragment();
                //exerciseFragment.setArguments(bundle);

                //getActivity().getSupportFragmentManager().beginTransaction()
                //        .replace(R.id.relativelayout, exerciseFragment)
                //        .commit();
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

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
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