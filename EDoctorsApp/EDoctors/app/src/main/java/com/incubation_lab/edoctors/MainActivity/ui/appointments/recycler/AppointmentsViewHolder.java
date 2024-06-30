package com.incubation_lab.edoctors.MainActivity.ui.appointments.recycler;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incubation_lab.edoctors.Models.DoctorDataModel;
import com.incubation_lab.edoctors.R;

import org.jetbrains.annotations.NotNull;

public class AppointmentsViewHolder extends RecyclerView.ViewHolder {

    TextView tvDoctorName,tvDesignation,tvQualification,tvDate,tvSerial;
    ImageView image;
    Button viewDetailsBtn;


    public AppointmentsViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        tvDoctorName =itemView.findViewById(R.id.appointment_child_doctor_name);
        tvDesignation = itemView.findViewById(R.id.appointment_child_doctor_designation);
        tvQualification=itemView.findViewById(R.id.appointment_child_doctor_qualifications);
        tvDate = itemView.findViewById(R.id.appointment_child_appointment_date);
        tvSerial=itemView.findViewById(R.id.appointment_child_appointment_serial);
        image = itemView.findViewById(R.id.appointment_child_doctor_image);
        viewDetailsBtn = itemView.findViewById(R.id.appointment_child_appointment_details_btn);
    }

}
