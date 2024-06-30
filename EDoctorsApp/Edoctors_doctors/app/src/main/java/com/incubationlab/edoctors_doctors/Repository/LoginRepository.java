package com.incubationlab.edoctors_doctors.Repository;

import static com.incubationlab.edoctors_doctors.StaticData.LoggedInDoctorData;

import android.util.Log;

import com.google.gson.JsonObject;
import com.incubationlab.edoctors_doctors.Models.DoctorDataModel;
import com.incubationlab.edoctors_doctors.Models.ResponseModel;
import com.incubationlab.edoctors_doctors.Repository.RemoteAPI.RetroInstance;
import com.incubationlab.edoctors_doctors.Repository.RemoteAPI.RetroInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {
    private RetroInterface retroInterface;
    public LoginRepository() {
        retroInterface = RetroInstance.getRetro();

    }

    public void register(DoctorDataModel doctorDataModel,RemoteRequestInterface requestInterface){
        Call<ResponseModel> call = retroInterface.register(doctorDataModel);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if(response.isSuccessful()) {
                    requestInterface.onSuccess(response.body().getCode(), response.body().getServerMsg());
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                requestInterface.onFailure(t.getMessage());

            }
        });
    }

    public void login(DoctorDataModel doctorDataModel, RemoteRequestInterface requestInterface){
        Call<DoctorDataModel> call = retroInterface.login(doctorDataModel);
        call.enqueue(new Callback<DoctorDataModel>() {
            @Override
            public void onResponse(Call<DoctorDataModel> call, Response<DoctorDataModel> response) {
                if(response.isSuccessful()) {
                    if (response.code() == 202) {
                        LoggedInDoctorData.setValue(response.body());
                    }
                    requestInterface.onSuccess(response.code(), response.body().getServerMsg());
                }
                else {
                    requestInterface.onFailure("Something went wrong!");
                }

            }

            @Override
            public void onFailure(Call<DoctorDataModel> call, Throwable t) {
                requestInterface.onFailure(t.getMessage());

            }
        });
    }


    public void findDoctorByPhone(String phoneNumber, RemoteRequestInterface requestInterface) {
        Call<ResponseModel> call = retroInterface.findDoctorByPhone(phoneNumber);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
               if(response.isSuccessful()&& response.code()==200){
                   requestInterface.onSuccess(response.body().getCode(),response.body().getServerMsg());
                   Log.i("responseData",response.body().getServerMsg() + response.body().getCode());
               }
               else {
                   requestInterface.onFailure("Something Went Wrong");
               }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                requestInterface.onFailure("Server error");

            }
        });
    }

    public void validateToken(String token,RemoteRequestInterface requestInterface) {
        Call<DoctorDataModel> call = retroInterface.loginWithToken(token);
        call.enqueue(new Callback<DoctorDataModel>() {
            @Override
            public void onResponse(Call<DoctorDataModel> call, Response<DoctorDataModel> response) {
                if(response.isSuccessful()){
                    if(response.code() == 202){
                        LoggedInDoctorData.setValue(response.body());
                    }
                    requestInterface.onSuccess(response.code(),response.body().getServerMsg());
                }
            }

            @Override
            public void onFailure(Call<DoctorDataModel> call, Throwable t) {
                requestInterface.onFailure(t.getMessage());

            }
        });
    }
}
