package com.incubationlab.edoctors_doctors.Models;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PatientDataModel implements Serializable {
    @SerializedName("first_name")
    @Expose
    private String firstName;
    @SerializedName("last_name")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("serverMsg")
    @Expose
    private String serverMsg;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;



    public PatientDataModel(String phone, String password) {
        this.password = password;
        this.phone = phone;
    }
    public PatientDataModel(String token) {
        this.token = token;
    }

    public PatientDataModel(String firstName, String lastName, String phone, String email, String age, String gender, String password  ) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;

        this.gender = gender;
        this.age = age;
        this.phone = phone;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getPhone() {
        return phone;
    }

    public String getServerMsg() {
        return serverMsg;
    }

    public String getToken() {
        return token;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
