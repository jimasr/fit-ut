
package com.example.myapplication.pojo.maps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geometry {

    @SerializedName("location")
    @Expose
    public Location location;
    @SerializedName("viewport")
    @Expose
    public Viewport viewport;

}
