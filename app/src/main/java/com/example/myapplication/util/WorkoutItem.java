package com.example.myapplication.util;

import com.example.myapplication.R;

public class WorkoutItem {
    String typeName;
    int numberOfExercise;
    int image;

    public WorkoutItem(String typeName, int NumberOfExercise, int image) {
        this.typeName = typeName;
        this.numberOfExercise = NumberOfExercise;
        this.image = image;
    }

    public WorkoutItem(String typeName, int NumberOfExercise) {
        this.typeName = typeName;
        this.numberOfExercise = NumberOfExercise;
        this.image = R.drawable.default_illustration;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getNumberOfExercise() {
        return numberOfExercise;
    }

    public void setNumberOfExercise(int numberOfExercise) {
        this.numberOfExercise = numberOfExercise;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
