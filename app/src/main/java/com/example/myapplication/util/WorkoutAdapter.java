package com.example.myapplication.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;

import java.util.List;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutHolder> {

    Context context;
    List<WorkoutItem> items;


    public WorkoutAdapter(Context context, List<WorkoutItem> items) {
        this.context = context;
        this.items = items;
    }


    @NonNull
    @Override
    public WorkoutHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.workout_item, parent, false);
        return new WorkoutHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHolder holder, int position) {
        holder.workoutTitle.setText(items.get(position).getTypeName());
        holder.numberOfExercise.setText(String.valueOf(items.get(position).getNumberOfExercise()));
        holder.workoutImage.setImageResource(items.get(position).getImage());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public WorkoutItem getWorkoutItem(int position){
        return items.get(position);
    }

}
