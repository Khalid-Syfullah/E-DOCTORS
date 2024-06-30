package com.incubation_lab.edoctors.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PrescriptionDataModel {

    @SerializedName("appointment_id")
    @Expose
    private String appointment_id;
    private ArrayList<MedicineDataModel> medicineDataModels;
    @SerializedName("date")
    @Expose
    private String date;



    @SerializedName("medicines")
    @Expose
    private String medicineString;

    public PrescriptionDataModel(String patientId, ArrayList<MedicineDataModel> medicineDataModels, String date) {
        this.appointment_id = patientId;
        this.medicineDataModels = medicineDataModels;
        this.date = date;
        medicineString = makeMedString(medicineDataModels);
    }

    private String makeMedString(ArrayList<MedicineDataModel> medicineDataModels) {

        StringBuilder medString = new StringBuilder();
        for (int i = 0; i < medicineDataModels.size(); i++) {
            MedicineDataModel medicineDataModel = medicineDataModels.get(i);
            medString.append(medicineDataModel.getMedicineName()).append("#r").append(medicineDataModel.getQtyMorning()).append("#r").append(medicineDataModel.getQtyDay()).append("#r").append(medicineDataModel.getQtyNight()).append("#r").append(medicineDataModel.getDaysOfCourse()).append("#n");

        }


        return medString.toString();

    }

    public String getAppointment_id() {
        return appointment_id;
    }

    public void setMedicineDataModels(ArrayList<MedicineDataModel> medicineDataModels) {
        this.medicineDataModels = medicineDataModels;
    }

    public ArrayList<MedicineDataModel> getMedicineDataModels() {
        return medicineDataModels;
    }

    public String getDate() {
        return date;
    }

    public String getMedicineString() {
        return medicineString;
    }
}
