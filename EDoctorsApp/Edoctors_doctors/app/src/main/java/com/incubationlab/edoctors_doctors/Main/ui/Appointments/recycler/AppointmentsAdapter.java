package com.incubationlab.edoctors_doctors.Main.ui.Appointments.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incubationlab.edoctors_doctors.Models.AppointmentDataModel;
import com.incubationlab.edoctors_doctors.R;

import java.util.ArrayList;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsViewHolder> {
    private ArrayList<AppointmentDataModel> appointmentDataModelList=new ArrayList<>();


    private AppointmentClickHandler appointmentClickHandler;

    public AppointmentsAdapter(AppointmentClickHandler appointmentClickHandler) {
        this.appointmentClickHandler = appointmentClickHandler;
    }

    @NonNull
    @Override
    public AppointmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_child,parent,false);
        return new AppointmentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentsViewHolder holder, int position) {

        AppointmentDataModel appointmentData = appointmentDataModelList.get(position);
        holder.tvName.setText(appointmentData.getPatientData().getFirstName() +" "+ appointmentData.getPatientData().getLastName());
        holder.tvAge.setText(appointmentData.getPatientData().getAge());
        holder.tvSerial.setText(appointmentData.getSerial());
        holder.tvDate.setText(appointmentData.getDate());
        holder.tvGender.setText(appointmentData.getPatientData().getGender());
        holder.viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointmentClickHandler.onclick(appointmentData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return appointmentDataModelList.size();
    }
    public void setData(ArrayList<AppointmentDataModel> appointmentDataModelList){
        this.appointmentDataModelList = appointmentDataModelList;
        notifyDataSetChanged();
    }
}
