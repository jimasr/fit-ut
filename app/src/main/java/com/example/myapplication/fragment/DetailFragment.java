package com.example.myapplication.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Exercise;
import com.example.myapplication.util.WorkoutItem;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import hallianinc.opensource.timecounter.StopWatch;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    private static View result;
    private ImageButton previousButton;
    private Button chronoButton;
    private TextView timer;
    private StopWatch chronometer;
    private long timeWhenStopped = 0;
    private Exercise exercise;
    private WorkoutItem workout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        result = inflater.inflate(R.layout.fragment_detail, container, false);

        previousButton = result.findViewById(R.id.exercise_previous_button);
        chronoButton = result.findViewById(R.id.chrono_button);

        chronometer = new StopWatch(result.findViewById(R.id.exercise_chronometer));

//        timer = result.findViewById(R.id.exercise_timer);
//        initTimer(timer);

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
            exercise = bundle.getParcelable("Exercise");
            workout = bundle.getParcelable("ActiveWorkout");

            //Setting correctly the text according to the given exercise
            exerciseNameTV.setText(exercise.getName());
            exerciseTypeTV.setText(exercise.getType());
            exerciseMuscleTV.setText("Muscle Targeted : " + exercise.getMuscle());
            exerciseEquipmentTV.setText("Equipment : " + exercise.getEquipment());
            exerciseDifficultyTV.setText("Difficulty : " + exercise.getDifficulty());
            exerciseInstructionsTV.setText(exercise.getInstructions());
        }

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("Workout", workout);

                ExerciseFragment exerciseFragment = new ExerciseFragment();
                exerciseFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.relativelayout, exerciseFragment)
                        .commit();
            }
        });

        chronoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = String.valueOf(chronoButton.getText());
                switch(text){
                    case "START":
                    case "RESUME":
                        chronometer.resume();
                        chronoButton.setText("STOP");
                        break;

                    case "STOP":
                        chronometer.pause();
                        chronoButton.setText("RESUME");
                        break;

                    default:
                        break;
                }
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

    public void initTimer(TextView textView) {
        new CountDownTimer(60000, 1000) {
            public void onTick(long millisUntilFinished) {
                // Used for formatting digit to be in 2 digits only
                NumberFormat f = new DecimalFormat("00");
                long min = (millisUntilFinished / 60000) % 60;
                long sec = (millisUntilFinished / 1000) % 60;
                textView.setText(f.format(min) + ":" + f.format(sec));
            }
            // When the task is over it will print 00:00 there
            public void onFinish() {
                textView.setText("00:00");
            }
        }.start();
    }
}