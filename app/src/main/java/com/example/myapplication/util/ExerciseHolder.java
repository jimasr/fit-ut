package com.example.myapplication.util;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class ExerciseHolder extends RecyclerView.ViewHolder{

    TextView exerciseTitle;
    TextView exerciseDifficulty;
    TextView exerciseMuscle;


    public ExerciseHolder(@NonNull View itemView) {
        super(itemView);
        exerciseTitle = itemView.findViewById(R.id.exercise_title_item);
        exerciseDifficulty = itemView.findViewById(R.id.exercise_difficulty_item);
        exerciseMuscle = itemView.findViewById(R.id.exercise_muscle_item);
    }
}
