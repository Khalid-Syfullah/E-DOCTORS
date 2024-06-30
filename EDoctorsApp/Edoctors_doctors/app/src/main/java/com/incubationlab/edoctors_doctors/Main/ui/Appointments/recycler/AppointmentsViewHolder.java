package com.incubationlab.edoctors_doctors.Main.ui.Appointments.recycler;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incubationlab.edoctors_doctors.R;

public class AppointmentsViewHolder extends RecyclerView.ViewHolder {
    TextView tvName,tvAge,tvGender,tvDate,tvSerial;
    ImageView patientImage;
    Button viewDetailsBtn;
    public AppointmentsViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.appointment_child_patient_name);
        tvAge = itemView.findViewById(R.id.appointment_child_patient_age);
        tvGender = itemView.findViewById(R.id.appointment_child_patient_gender);
        tvDate = itemView.findViewById(R.id.appointment_child_appointment_date);
        tvSerial = itemView.findViewById(R.id.appointment_child_appointment_serial);
        patientImage = itemView.findViewById(R.id.appointment_child_patinet_image);
        viewDetailsBtn = itemView.findViewById(R.id.appointment_child_view_details_btn);
    }


}
