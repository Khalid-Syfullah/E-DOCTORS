package com.incubation_lab.edoctors.Models;

import static com.incubation_lab.edoctors.Repository.Remote.RetroInstance.BASE_URL;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DoctorDataModel implements Serializable {

    @Expose
    @SerializedName("first_name")
    private String firstName;

    @Expose
    @SerializedName("last_name")
    private  String lastName;

    @Expose
    private String designation;

    @Expose
    private String qualifications;
    @SerializedName("starting_time")
    @Expose
    private String startTime;

    @Expose
    private String gender;


    @SerializedName("bmdc")
    @Expose
    private String bmdc;

    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("password")
    @Expose
    private String password;
    @Expose
    private String bio;
    @Expose
    private String fee;
    @Expose
    private String speciality;

    @Expose
    private String token;

    @SerializedName("image_url")
    @Expose
    private String imageUrl;

    @Expose
    private String serverMsg;





    @SerializedName("rating")
    @Expose
    private String rating;




    public DoctorDataModel(String bmdc, String password) {
        this.bmdc = bmdc;
        this.password = password;
    }

    public DoctorDataModel() {

    }

    public String getName() {
        return firstName+" " +lastName;
    }

    public String getBmdc() {
        return bmdc;
    }

    public String getCurrentDesignation() {
        return designation;
    }

    public String getBio() {
        return bio;
    }

    public String getQualifications() {
        return qualifications;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getFee() {
        return fee;
    }

    public String getPhone() {
        return phone;
    }

    public String getServerMsg() {
        return serverMsg;
    }

    public String getImageUrl() {
        return BASE_URL + "/" +imageUrl;
    }
    public String getRating() {
        return rating;
    }


}
