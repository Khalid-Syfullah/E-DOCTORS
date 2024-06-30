package com.incubationlab.edoctors_doctors.Main.ui.Appointments;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.incubationlab.edoctors_doctors.Models.AppointmentDataModel;
import com.incubationlab.edoctors_doctors.Models.PrescriptionDataModel;
import com.incubationlab.edoctors_doctors.Repository.AppointmentsRepository;
import com.incubationlab.edoctors_doctors.Repository.RemoteRequestInterface;

import java.util.ArrayList;

public class AppointmentsViewModel extends ViewModel {

    private AppointmentsRepository appointmentsRepository;
    public AppointmentsViewModel() {
        appointmentsRepository = new AppointmentsRepository();

    }

    public void fetchAppointments(RemoteRequestInterface requestInterface){
        appointmentsRepository.fetchAppointments(requestInterface);
    }
    public LiveData<ArrayList<AppointmentDataModel>> getAppointments(){
        return appointmentsRepository.getAppointmentsData();

    }

    public void updateCurrentSerial(AppointmentDataModel appointmentDataModel,RemoteRequestInterface requestInterface) {
        appointmentsRepository.updateCurrentSerial(appointmentDataModel,requestInterface);
    }

    public void addPrescription(PrescriptionDataModel prescriptionDataModel, RemoteRequestInterface requestInterface) {
        appointmentsRepository.addPrescription(prescriptionDataModel,requestInterface);
    }




}
