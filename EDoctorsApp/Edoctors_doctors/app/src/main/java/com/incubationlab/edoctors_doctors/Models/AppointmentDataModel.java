package com.incubationlab.edoctors_doctors.Models;



import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AppointmentDataModel implements Serializable {

    @Expose
    @SerializedName("patient_id")
    private String patientId;

    @Expose
    @SerializedName("doctor_id")
    private String doctorId;

    @Expose
    private String date;



    @Expose
    @SerializedName("serial")
    private String serial;

    @Expose
    private String link;


    @Expose
    @SerializedName("patientData")
    private PatientDataModel patientData;
    @Expose
    @SerializedName("doctorData")
    private DoctorDataModel doctorDataModel;

    @Expose
    private String serverMsg;

    public AppointmentDataModel(String patientId, String doctorId, String date) {
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
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

    public PatientDataModel getPatientData() {
        return patientData;
    }
    public DoctorDataModel getDoctorDataModel() {
        return doctorDataModel;
    }

}
