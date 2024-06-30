package com.incubation_lab.edoctors.MainActivity.ui.appointments;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;


import com.incubation_lab.edoctors.Models.AppointmentDataModel;
import com.incubation_lab.edoctors.Models.PrescriptionDataModel;
import com.incubation_lab.edoctors.Repository.AppointmentsRepository;
import com.incubation_lab.edoctors.Repository.Remote.PrescriptionGetterInterface;
import com.incubation_lab.edoctors.Repository.Remote.RemoteRequestInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class AppointmentsViewModel extends AndroidViewModel {

    private AppointmentsRepository appointmentsRepository;

    public AppointmentsViewModel(@NonNull @NotNull Application application) {
        super(application);
        appointmentsRepository = new AppointmentsRepository(application);
    }

    public LiveData<ArrayList<AppointmentDataModel>> getDoctorAppointmentList(String doctorId){
        return appointmentsRepository.getDoctorAppointmentList(doctorId);
    }

    public LiveData<ArrayList<AppointmentDataModel>> getPatientAppointmentList(String patientId) {
        return appointmentsRepository.getPatientAppointmentList(patientId);
    }

    public LiveData<String> updateCurrentSerial(AppointmentDataModel appointmentDataModel) {
        return appointmentsRepository.updateCurrentSerial(appointmentDataModel);
    }

    public LiveData<String> getCurrentSerial(String doctorId) {

        return appointmentsRepository.getCurrentSerial(doctorId);
    }
    public void getPrescription(String appointment_id, PrescriptionGetterInterface prescriptionGetterInterface){
         appointmentsRepository.getPatientPrescription(appointment_id,prescriptionGetterInterface);
    }

    public void addReview(AppointmentDataModel appointmentDataModel, RemoteRequestInterface requestInterface) {
        appointmentsRepository.addReview(appointmentDataModel,requestInterface);
    }
}