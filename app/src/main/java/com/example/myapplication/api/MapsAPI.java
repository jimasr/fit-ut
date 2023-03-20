package com.example.myapplication.api;

import com.example.myapplication.entity.Place;
import com.example.myapplication.pojo.maps.MapsPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapsAPI {

//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=45.777387699793756%2C%204.893445424569205&radius=1000&type=gym&keyword=gym&key=AIzaSyCXlSLKISsXZTvwglXG6CXkPCvXroTJBDE
    @GET("/maps/api/place/nearbysearch/json")
    Call<MapsPojo> getPlaces(
            @Query("location") String location,
            @Query("radius") Integer radius,
            @Query("type") String type,
            @Query("keyword") String keyword,
            @Query("key") String key
    );


}
