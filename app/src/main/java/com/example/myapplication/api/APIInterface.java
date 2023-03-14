package com.example.myapplication.api;

import com.example.myapplication.entity.Exercise;
import com.example.myapplication.pojo.MultipleResource;
import com.example.myapplication.util.WorkoutItem;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIInterface {

    String BASE_URL = "https://api.api-ninjas.com/v1";
    @GET("/exercises")
    Call<Exercise> doGetListResources();

    @POST("/api/workout")
    Call<WorkoutItem> createWorkout(@Body WorkoutItem workout);

    @GET("/api/workout?")
    Call<ArrayList<WorkoutItem>> doGetWorkoutList(@Query("page") String page);

    @FormUrlEncoded
    @POST("/api/workout?")
    Call<ArrayList<WorkoutItem>> doCreateWorkoutWithField(@Field("type_name") String name);
}
