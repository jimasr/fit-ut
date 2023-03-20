package com.example.myapplication.util;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.myapplication.R;

public class WorkoutItem implements Parcelable {
    String typeName;
    String apiParam;
    int numberOfExercise;
    int image;


    public WorkoutItem(String typeName, int NumberOfExercise, int image) {
        this.typeName = typeName;
        this.numberOfExercise = NumberOfExercise;
        this.image = image;
    }

    public WorkoutItem(String typeName, String apiParam, int NumberOfExercise) {
        this.typeName = typeName;
        this.apiParam = apiParam;
        this.numberOfExercise = NumberOfExercise;
        this.image = R.drawable.default_illustration;
    }

    protected WorkoutItem(Parcel in) {
        typeName = in.readString();
        numberOfExercise = in.readInt();
        image = in.readInt();
    }

    public static final Creator<WorkoutItem> CREATOR = new Creator<WorkoutItem>() {
        @Override
        public WorkoutItem createFromParcel(Parcel in) {
            return new WorkoutItem(in);
        }

        @Override
        public WorkoutItem[] newArray(int size) {
            return new WorkoutItem[size];
        }
    };

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getApiParam() {
        return apiParam;
    }

    public void setApiParam(String apiParam) {
        this.apiParam = apiParam;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(typeName);
        parcel.writeInt(numberOfExercise);
        parcel.writeInt(image);
    }
}
