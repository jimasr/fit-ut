package com.example.myapplication.util;

public class WorkoutItem {
    String type_name;
    String number_of_exercise;
    int image;

    public WorkoutItem(String type_name, String number_of_exercise, int image) {
        this.type_name = type_name;
        this.number_of_exercise = number_of_exercise;
        this.image = image;
    }

    public WorkoutItem(String type_name, String number_of_exercise) {
        this.type_name = type_name;
        this.number_of_exercise = number_of_exercise;
        this.image = 0;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getNumber_of_exercise() {
        return number_of_exercise;
    }

    public void setNumber_of_exercise(String number_of_exercise) {
        this.number_of_exercise = number_of_exercise;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
