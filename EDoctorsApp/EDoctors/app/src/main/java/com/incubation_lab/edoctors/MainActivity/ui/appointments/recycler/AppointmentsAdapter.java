package com.incubation_lab.edoctors.MainActivity.ui.appointments.recycler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incubation_lab.edoctors.Models.AppointmentDataModel;
import com.incubation_lab.edoctors.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static com.incubation_lab.edoctors.Repository.Remote.RetroInstance.BASE_URL;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsViewHolder> {

    private ArrayList<AppointmentDataModel> appointmentDataModelList=new ArrayList<>();
    private String mode;
    private AppointmentClickHandler appointmentClickHandler;

    public AppointmentsAdapter(String mode,AppointmentClickHandler appointmentClickHandler) {
        this.mode = mode;
        this.appointmentClickHandler = appointmentClickHandler;
    }

    @NonNull
    @NotNull
    @Override
    public AppointmentsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_child,parent,false);
        AppointmentsViewHolder vh=new AppointmentsViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull AppointmentsViewHolder holder, int position) {
        AppointmentDataModel appointmentDataModel = appointmentDataModelList.get(position);


        holder.tvSerial.setText(appointmentDataModel.getSerial());
        holder.tvDate.setText(appointmentDataModel.getDate());

            holder.tvQualification.setText(appointmentDataModel.getDoctorDataModel().getQualifications());
            holder.tvDesignation.setText(appointmentDataModel.getDoctorDataModel().getCurrentDesignation());
            holder.tvDoctorName.setText(appointmentDataModel.getDoctorDataModel().getName());
            if(appointmentDataModel.getDoctorDataModel().getImageUrl().toLowerCase().equals("not set")){
                holder.image.setImageResource(R.drawable.account);
            }
            else
                Picasso.get().load(appointmentDataModel.getDoctorDataModel().getImageUrl()).placeholder(R.drawable.account).into(holder.image);


        holder.viewDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appointmentClickHandler.onclick(appointmentDataModel);
            }
        });


    }

    @Override
    public int getItemCount() {
        return appointmentDataModelList.size();
    }

    public void setAppointmentData(ArrayList<AppointmentDataModel> appointmentDataModels) {
        this.appointmentDataModelList = appointmentDataModels;
        notifyDataSetChanged();
    }
}
