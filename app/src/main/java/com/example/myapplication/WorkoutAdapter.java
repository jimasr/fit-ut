package com.example.myapplication;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

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
        return new WorkoutHolder(LayoutInflater.from(context).inflate(R.layout.workout_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutHolder holder, int position) {
        holder.workout_title.setText(items.get(position).getType_name());
        holder.number_of_exercise.setText(items.get(position).getNumber_of_exercise());
        holder.workout_image.setImageResource(items.get(position).getImage());
    }


    @Override
    public int getItemCount() {
        return items.size();
    }
}
