package com.incubationlab.edoctors_doctors;

import androidx.lifecycle.MutableLiveData;

import com.incubationlab.edoctors_doctors.Models.DoctorDataModel;

public class StaticData {
    public static String DOCTOR_LOGIN_SHARED_PREFS = "doctor login shared prefs";
    public static String DOCTOR_ACCESS_TOKEN = "doctor access token";
    public static MutableLiveData<DoctorDataModel> LoggedInDoctorData = new MutableLiveData<>();
}
