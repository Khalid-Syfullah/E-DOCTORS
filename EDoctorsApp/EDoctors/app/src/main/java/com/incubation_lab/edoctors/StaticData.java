package com.incubation_lab.edoctors;

import androidx.lifecycle.MutableLiveData;

import com.incubation_lab.edoctors.Models.UserDataModel;

public class StaticData {
    public static final String BUNDLE_KEY="key";
    public static final String DOCTOR_BUNDLE_KEY = "selected_doctor";
    public static final String KEY_FORGOT_PASS="forgotPass";
    public static final String KEY_REGISTER="register";
    public static final String USER_EXISTS="user already exists";
    public static final String STATUS_LOGGED_IN="logged in";
    public static final String STATUS_PASSWORD_UPDATED="password updated";
    public static final String STATUS_LOG_IN_REQUIRED="login required";
    public static final String STATUS_REGISTERED="registered";
    public static final String USER_NOT_FOUND="user not found";
    public static final String RESPONSE_SUCCESS="success";
    public static final String PICTURE_UPDATE_SUCCESS="picture updated";
    public static MutableLiveData<UserDataModel> LoggedInUserData =new MutableLiveData<>();

    public static MutableLiveData<Integer> LoginCurrentFragment=new MutableLiveData<>();


}
