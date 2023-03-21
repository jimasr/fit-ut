package com.example.myapplication.api;

import com.example.myapplication.BuildConfig;
import com.example.myapplication.entity.Exercise;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface NinjaAPI {

    @Headers("X-Api-Key:" + BuildConfig.NINJA_API_KEY)
    @GET("/v1/exercises")
    Call<List<Exercise>> getAllData();

    @Headers("X-Api-Key:" + BuildConfig.NINJA_API_KEY)
    @GET("/v1/exercises")
    Call<List<Exercise>> getDataByType(@Query("type") String type);

    @Headers("X-Api-Key:" + BuildConfig.NINJA_API_KEY)
    @GET("/v1/exercises")
    Call<List<Exercise>> getDataByMuscle(@Query("muscle") String type);
}
