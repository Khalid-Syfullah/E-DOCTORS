package com.incubationlab.edoctors_doctors.Repository;



import com.incubationlab.edoctors_doctors.Models.DoctorDataModel;
import com.incubationlab.edoctors_doctors.Models.ResponseModel;
import com.incubationlab.edoctors_doctors.Repository.RemoteAPI.RetroInstance;
import com.incubationlab.edoctors_doctors.Repository.RemoteAPI.RetroInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsRepository {
    private RetroInterface retroInterface;
    public void setStatus(DoctorDataModel doctorDataModel) {
        retroInterface = RetroInstance.getRetro();
        Call<ResponseModel> call = retroInterface.setStatus(doctorDataModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }
    public void unSetStatus(DoctorDataModel doctorDataModel) {
        Call<ResponseModel> call = retroInterface.unSetStatus(doctorDataModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }
}
