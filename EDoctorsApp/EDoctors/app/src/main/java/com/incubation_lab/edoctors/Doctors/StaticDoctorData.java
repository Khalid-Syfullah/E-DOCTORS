package com.incubation_lab.edoctors.Doctors;

import androidx.lifecycle.MutableLiveData;

import com.incubation_lab.edoctors.Models.DoctorDataModel;


public class StaticDoctorData {
    public static MutableLiveData<DoctorDataModel> loggedInDoctorData=new MutableLiveData<>();
}
