
package com.example.myapplication.pojo.maps;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MapsPojo {

    @SerializedName("html_attributions")
    @Expose
    public List<Object> htmlAttributions;
    @SerializedName("results")
    @Expose
    public List<Result> results;
    @SerializedName("status")
    @Expose
    public String status;

}
