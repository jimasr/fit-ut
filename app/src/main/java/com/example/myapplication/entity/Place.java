package com.example.myapplication.entity;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

public class Place {
    private String name;
    private String vicinity;
    private LatLng latLng;

    public Place(String name, String vicinity, LatLng location) {
        this.name = name;
        this.vicinity = vicinity;
        this.latLng = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
}
