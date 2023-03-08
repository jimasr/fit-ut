package com.example.myapplication.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

public class WorkoutHolder extends RecyclerView.ViewHolder {

    TextView workout_title;
    TextView number_of_exercise;
    ImageView workout_image;

    public WorkoutHolder(@NonNull View itemView) {
        super(itemView);
        workout_title = itemView.findViewById(R.id.workout_title);
        number_of_exercise = itemView.findViewById(R.id.number_exercise);
        workout_image = itemView.findViewById(R.id.workout_imageview);
    }
}
