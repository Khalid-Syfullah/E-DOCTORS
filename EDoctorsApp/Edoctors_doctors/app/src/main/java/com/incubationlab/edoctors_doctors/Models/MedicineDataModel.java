package com.incubationlab.edoctors_doctors.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MedicineDataModel {
    @SerializedName("med_name")
    @Expose
    private String medicineName;
    @SerializedName("qty_m")
    @Expose
    private String qtyMorning;
    @SerializedName("qty_d")
    @Expose
    private String qtyDay;
    @SerializedName("qty_n")
    @Expose
    private String qtyNight;
    @SerializedName("days_of_course")
    @Expose
    private String daysOfCourse;

    public MedicineDataModel(String medicineName, String qtyMorning, String qtyDay, String qtyNight, String daysOfCourse) {
        this.medicineName = medicineName;
        this.qtyMorning = qtyMorning;
        this.qtyDay = qtyDay;
        this.qtyNight = qtyNight;
        this.daysOfCourse = daysOfCourse;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public String getQtyMorning() {
        return qtyMorning;
    }

    public String getQtyDay() {
        return qtyDay;
    }

    public String getQtyNight() {
        return qtyNight;
    }

    public String getDaysOfCourse() {
        return daysOfCourse;
    }
}
