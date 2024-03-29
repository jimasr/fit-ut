
package com.example.myapplication.pojo.maps;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("height")
    @Expose
    public Integer height;
    @SerializedName("html_attributions")
    @Expose
    public List<String> htmlAttributions;
    @SerializedName("photo_reference")
    @Expose
    public String photoReference;
    @SerializedName("width")
    @Expose
    public Integer width;

}
