package com.incubation_lab.edoctors.MainActivity.ui.doctors;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.incubation_lab.edoctors.Models.AppointmentDataModel;
import com.incubation_lab.edoctors.Models.DoctorDataModel;

import com.incubation_lab.edoctors.Models.ReviewDataModel;
import com.incubation_lab.edoctors.Repository.DoctorsRepository;
import com.incubation_lab.edoctors.Repository.Remote.OnReviewReceivedInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DoctorsViewModel extends AndroidViewModel {

    private DoctorsRepository doctorsRepository;

    public DoctorsViewModel(@NonNull @NotNull Application application) {
        super(application);
        doctorsRepository=new DoctorsRepository(application);
        doctorsRepository.fetchAllDoctors();
    }
    public LiveData<ArrayList<DoctorDataModel>> getAllDoctors(){
        return doctorsRepository.getAllDoctors();
    }

    public void getAppointment(AppointmentDataModel appointmentDataModel) {
        doctorsRepository.getAppointment(appointmentDataModel);
    }
    public void getReviews(String doctorId, OnReviewReceivedInterface onReviewReceivedInterface){
         doctorsRepository.getReviews(doctorId,onReviewReceivedInterface);
    }


}