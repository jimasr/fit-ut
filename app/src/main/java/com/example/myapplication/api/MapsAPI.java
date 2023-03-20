package com.example.myapplication.api;

import com.example.myapplication.entity.Place;
import com.example.myapplication.pojo.maps.MapsPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapsAPI {

    @GET("/maps/api/place/nearbysearch/json")
    Call<MapsPojo> getPlaces(
            @Query("location") String location,
            @Query("radius") Integer radius,
            @Query("type") String type,
            @Query("keyword") String keyword,
            @Query("key") String key
    );


}
