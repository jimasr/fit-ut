
package com.example.myapplication.pojo.maps;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Result {

    @SerializedName("business_status")
    @Expose
    public String businessStatus;
    @SerializedName("geometry")
    @Expose
    public Geometry geometry;
    @SerializedName("icon")
    @Expose
    public String icon;
    @SerializedName("icon_background_color")
    @Expose
    public String iconBackgroundColor;
    @SerializedName("icon_mask_base_uri")
    @Expose
    public String iconMaskBaseUri;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("opening_hours")
    @Expose
    public OpeningHours openingHours;
    @SerializedName("photos")
    @Expose
    public List<Photo> photos;
    @SerializedName("place_id")
    @Expose
    public String placeId;
    @SerializedName("plus_code")
    @Expose
    public PlusCode plusCode;
    @SerializedName("rating")
    @Expose
    public Double rating;
    @SerializedName("reference")
    @Expose
    public String reference;
    @SerializedName("scope")
    @Expose
    public String scope;
    @SerializedName("types")
    @Expose
    public List<String> types;
    @SerializedName("user_ratings_total")
    @Expose
    public Integer userRatingsTotal;
    @SerializedName("vicinity")
    @Expose
    public String vicinity;

}
