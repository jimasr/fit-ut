package com.example.myapplication.workout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class WorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        RecyclerView recyclerView = findViewById(R.id.workout_recyclerview);

        List<WorkoutItem> items = new ArrayList<WorkoutItem>();
        //Test
        items.add(new WorkoutItem("Cardio", "10", R.drawable.ic_launcher_background));
        items.add(new WorkoutItem("Strength", "15", R.drawable.ic_launcher_background));
        items.add(new WorkoutItem("StrongMan", "2", R.drawable.ic_launcher_background));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(new WorkoutAdapter(getApplicationContext(), items));

    }
}