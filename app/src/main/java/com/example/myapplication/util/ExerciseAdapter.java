package com.example.myapplication.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entity.Exercise;

import java.util.List;


public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseHolder>{

    Context context;
    List<Exercise> items;

    public ExerciseAdapter(Context context, List<Exercise> items) {
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ExerciseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseHolder holder, int position) {
        holder.exerciseTitle.setText(items.get(position).getName());
        holder.exerciseDifficulty.setText(items.get(position).getDifficulty());
        holder.exerciseMuscle.setText(items.get(position).getMuscle());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public Exercise getExerciseItem(int position){
        return items.get(position);
    }
}
