package com.incubationlab.edoctors_doctors.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorDataModel {

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
    private String doctorId;

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

    @Expose
    private boolean status;
    @SerializedName("max_patients")
    @Expose
    private int maxPatients;


    public DoctorDataModel(String doctorId, String phone, String password) {
        this.doctorId = doctorId;
        this.phone = phone;
        this.password = password;
    }

    public DoctorDataModel(String phoneNumber) {
        this.phone = phoneNumber;
    }
    public DoctorDataModel(String firstName, String lastName, String designation, String qualifications, String startTime, String gender, String doctorId, String phone,String fee,String bio,String speciality, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.designation = designation;
        this.qualifications = qualifications;
        this.startTime = startTime;
        this.gender = gender;
        this.doctorId = doctorId;
        this.phone = phone;
        this.password = password;
        this.fee= fee;
        this.bio = bio;
        this.speciality = speciality;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getDesignation() {
        return designation;
    }

    public String getQualifications() {
        return qualifications;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getGender() {
        return gender;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getBio() {
        return bio;
    }

    public String getFee() {
        return fee;
    }

    public String getSpeciality() {
        return speciality;
    }

    public String getToken() {
        return token;
    }

    public String getServerMsg() {
        return serverMsg;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isStatus() {
        return status;
    }

    public int getMaxPatients() {
        return maxPatients;
    }
}
