package com.example.myapplication.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Exercise implements Parcelable {

    private String name;

    private String type;

    private String muscle;

    private String equipment;

    private String difficulty;

    private String instructions;

    public Exercise(String name, String type, String muscle, String equipment, String difficulty, String instructions) {
        this.name = name;
        this.type = type;
        this.muscle = muscle;
        this.equipment = equipment;
        this.difficulty = difficulty;
        this.instructions = instructions;
    }

    protected Exercise(Parcel in) {
        name = in.readString();
        type = in.readString();
        muscle = in.readString();
        equipment = in.readString();
        difficulty = in.readString();
        instructions = in.readString();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getMuscle() {
        return muscle;
    }

    public String getEquipment() {
        return equipment;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getInstructions() {
        return instructions;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(muscle);
        parcel.writeString(equipment);
        parcel.writeString(difficulty);
        parcel.writeString(instructions);
    }
}
