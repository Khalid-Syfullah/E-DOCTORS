package com.incubation_lab.edoctors.MainActivity.ui.doctors.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incubation_lab.edoctors.R;

import org.jetbrains.annotations.NotNull;

public class DoctorsViewHolder extends RecyclerView.ViewHolder {
    TextView name,currentDesignation,speciality,feeText,tvRating;
    RatingBar ratingBar;
    ImageView doctorImage;
    public DoctorsViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.doctor_name);
        speciality=itemView.findViewById(R.id.doctor_designation);
        currentDesignation=itemView.findViewById(R.id.doctor_qualification);
        feeText=itemView.findViewById(R.id.doctor_fee);
        doctorImage=itemView.findViewById(R.id.doctor_image);
        ratingBar = itemView.findViewById(R.id.doctors_ratingBar);
        tvRating = itemView.findViewById(R.id.tv_rating);

    }
}
