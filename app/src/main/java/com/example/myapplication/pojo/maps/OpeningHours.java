
package com.example.myapplication.pojo.maps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OpeningHours {

    @SerializedName("open_now")
    @Expose
    public Boolean openNow;

}
