package com.incubationlab.edoctors_doctors.Repository;

import static com.incubationlab.edoctors_doctors.StaticData.LoggedInDoctorData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.incubationlab.edoctors_doctors.Models.AppointmentDataModel;
import com.incubationlab.edoctors_doctors.Models.PrescriptionDataModel;
import com.incubationlab.edoctors_doctors.Models.ResponseModel;
import com.incubationlab.edoctors_doctors.Repository.RemoteAPI.RetroInstance;
import com.incubationlab.edoctors_doctors.Repository.RemoteAPI.RetroInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AppointmentsRepository {
    private RetroInterface retroInterface;
    private MutableLiveData<ArrayList<AppointmentDataModel>> appointmentsData;

    public AppointmentsRepository() {
        retroInterface = RetroInstance.getRetro();
        appointmentsData = new MutableLiveData<>();
    }

    public void fetchAppointments(RemoteRequestInterface requestInterface) {
        Call<ArrayList<AppointmentDataModel>> call = retroInterface.getAppointments(LoggedInDoctorData.getValue().getDoctorId());
        call.enqueue(new Callback<ArrayList<AppointmentDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<AppointmentDataModel>> call, Response<ArrayList<AppointmentDataModel>> response) {
                if (response.isSuccessful()){
                    if(response.code()==200) {
                        appointmentsData.setValue(response.body());
                        requestInterface.onSuccess(response.code(),"success");
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AppointmentDataModel>> call, Throwable t) {
                requestInterface.onFailure(t.getMessage());

            }
        });

    }


    public LiveData<ArrayList<AppointmentDataModel>> getAppointmentsData() {
        if(appointmentsData.getValue()==null){
            fetchAppointments(null);
        }
        return appointmentsData;
    }

    public void updateCurrentSerial(AppointmentDataModel appointmentDataModel,RemoteRequestInterface requestInterface) {

        Call<AppointmentDataModel> call = retroInterface.updateCurrentSerial(appointmentDataModel);
        call.enqueue(new Callback<AppointmentDataModel>() {
            @Override
            public void onResponse(Call<AppointmentDataModel> call, Response<AppointmentDataModel> response) {
                if(response.isSuccessful() && response.code()==200 ){
                    requestInterface.onSuccess(200,"success");
                }
                else {
                    requestInterface.onFailure("Failed");
                }
            }

            @Override
            public void onFailure(Call<AppointmentDataModel> call, Throwable t) {
                requestInterface.onFailure("Server Error");

            }
        });
    }

    public void addPrescription(PrescriptionDataModel prescriptionDataModel, RemoteRequestInterface requestInterface) {
        Call<ResponseModel> call = retroInterface.addPrescription(prescriptionDataModel);
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
