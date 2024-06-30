package com.incubation_lab.edoctors.Models;


import static com.incubation_lab.edoctors.Repository.Remote.RetroInstance.BASE_URL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BlogDataModel implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("doctor_id")
    @Expose
    private String doctorId;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @Expose
    private String date;

    @SerializedName("old_blog_data")
    @Expose
    private BlogDataModel blogData;

    public BlogDataModel(String doctorId, String title, String image, String description) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.doctorId = doctorId;
    }

    public BlogDataModel(BlogDataModel blogData, String doctorId, String title, String image, String description) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.doctorId = doctorId;
        this.blogData = blogData;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return BASE_URL+"/"+ imageUrl;
    }

    public String getDescription() {
        return description;
    }
    public String getDate() {

        return date;
    }

    public String getDoctorId() {
        return doctorId;
    }


}
