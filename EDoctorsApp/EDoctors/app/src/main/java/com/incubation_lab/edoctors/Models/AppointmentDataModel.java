package com.incubation_lab.edoctors.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AppointmentDataModel implements Serializable {

    @Expose
    @SerializedName("patient_id")
    String patientId;

    @Expose
    @SerializedName("doctor_id")
    String doctorId;

    @Expose
    String date;



    @Expose
    @SerializedName("serial")
    String serial;

    @Expose
    String link;


    @Expose
    @SerializedName("patientData")
    UserDataModel patientData;
    @Expose
    @SerializedName("doctorData")
    DoctorDataModel doctorDataModel;

    @Expose
    @SerializedName("rating")
    String rating;

    @Expose
    @SerializedName("review")
    String review;

    @Expose
    @SerializedName("type")
    String type;

    @Expose
    @SerializedName("symptomMsg")
    String symptom;

    @Expose
    String serverMsg;

    public AppointmentDataModel(String patientId, String doctorId, String date,String symptom,String type) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.symptom = symptom;
        this.type = type;
    }
    public AppointmentDataModel(String doctorId,String serial){
        this.doctorId = doctorId;
        this.serial =serial;
    }

    public String getPatientId() {
        return patientId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public String getDate() {
        return date;
    }

    public String getServerMsg() {
        return serverMsg;
    }

    public String getSerial() {
        return serial;
    }

    public String getLink() {
        return link;
    }

    public UserDataModel getPatientData() {
        return patientData;
    }
    public DoctorDataModel getDoctorDataModel() {
        return doctorDataModel;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRating() {
        return rating;
    }

    public String getReview() {
        return review;
    }

    public String getType() {
        return type;
    }

    public String getSymptom() {
        return symptom;
    }
}
