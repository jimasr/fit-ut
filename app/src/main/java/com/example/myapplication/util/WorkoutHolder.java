package com.example.myapplication.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class WorkoutHolder extends RecyclerView.ViewHolder {

    TextView workoutTitle;
    TextView numberOfExercise;
    ImageView workoutImage;

    public WorkoutHolder(@NonNull View itemView) {
        super(itemView);
        workoutTitle = itemView.findViewById(R.id.workout_title);
        numberOfExercise = itemView.findViewById(R.id.number_exercise);
        workoutImage = itemView.findViewById(R.id.workout_imageview);
    }
}
