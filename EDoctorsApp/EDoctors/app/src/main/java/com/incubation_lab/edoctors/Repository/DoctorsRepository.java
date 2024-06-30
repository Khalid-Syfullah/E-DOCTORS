package com.incubation_lab.edoctors.Repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.incubation_lab.edoctors.Models.AppointmentDataModel;
import com.incubation_lab.edoctors.Models.DoctorDataModel;
import com.incubation_lab.edoctors.Models.ReviewDataModel;
import com.incubation_lab.edoctors.Repository.Remote.OnReviewReceivedInterface;
import com.incubation_lab.edoctors.Repository.Remote.RetroInstance;
import com.incubation_lab.edoctors.Repository.Remote.RetroInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorsRepository {
    private Application application;
    private RetroInterface retroInterface;


    private static MutableLiveData<ArrayList<DoctorDataModel>> allDoctors =new MutableLiveData<>();


    public DoctorsRepository(Application application) {
        retroInterface= RetroInstance.getRetro();
        this.application= application;


    }
    public void fetchAllDoctors(){
        Call<ArrayList<DoctorDataModel>> call = retroInterface.getAllDoctors();
        call.enqueue(new Callback<ArrayList<DoctorDataModel>> () {
            @Override
            public void onResponse(Call<ArrayList<DoctorDataModel>> call, Response<ArrayList<DoctorDataModel>> response) {
                if(response.isSuccessful()){
                   allDoctors.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<DoctorDataModel>> call, Throwable t) {

                Toast.makeText(application, "Network error", Toast.LENGTH_SHORT).show();

            }
        });
    }
    
    public LiveData<ArrayList<DoctorDataModel>> getAllDoctors() {
        return allDoctors;
    }

    public void getAppointment(AppointmentDataModel appointmentDataModel) {
        Call<AppointmentDataModel> call = retroInterface.getAppointment(appointmentDataModel);
        call.enqueue(new Callback<AppointmentDataModel>() {
            @Override
            public void onResponse(Call<AppointmentDataModel> call, Response<AppointmentDataModel> response) {
                if(response.isSuccessful() && response.code()==200){
                    Toast.makeText(application, response.body().getServerMsg(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AppointmentDataModel> call, Throwable t) {
                Toast.makeText(application, "Something Went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void getReviews(String doctorId, OnReviewReceivedInterface onReviewReceivedInterface) {
        retroInterface.getReviews(doctorId).enqueue(new Callback<ArrayList<ReviewDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ReviewDataModel>> call, Response<ArrayList<ReviewDataModel>> response) {
                if(response.isSuccessful()){
                    if(response.code()==200){
                        onReviewReceivedInterface.onReceived(response.body());
                    }
                }


            }

            @Override
            public void onFailure(Call<ArrayList<ReviewDataModel>> call, Throwable t) {
                onReviewReceivedInterface.onFailed();

            }
        });
    }
}
