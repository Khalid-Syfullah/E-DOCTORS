package com.incubation_lab.edoctors.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewDataModel {
    @Expose
    @SerializedName("rating")
    private String rating;
    @Expose
    @SerializedName("review")
    private String review;

    @Expose
    @SerializedName("review_date")
    private String date;


    public ReviewDataModel() {
    }

    public String getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public String getDate() {
        return date;
    }
}
