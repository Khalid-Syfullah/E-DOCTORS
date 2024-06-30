package com.incubation_lab.edoctors.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserImageModel {
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("serverMsg")
    @Expose
    private String serverMsg;
    @SerializedName("image")
    @Expose
    private String encodedImage;

    public UserImageModel(String phone, String encodedImage) {
        this.phone = phone;
        this.encodedImage = encodedImage;
    }

    public String getServerMsg() {
        return serverMsg;
    }
}
