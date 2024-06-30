package com.incubation_lab.edoctors.MainActivity.ui.appointments.recycler;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incubation_lab.edoctors.R;


public class PrescriptionViewHolder extends RecyclerView.ViewHolder {
    TextView medName,qtyMorning,qtyDay,qtyNight,courseDays;

    public PrescriptionViewHolder(@NonNull View itemView) {
        super(itemView);
        medName = itemView.findViewById(R.id.pres_child_med_name);
        qtyDay = itemView.findViewById(R.id.pres_child_qty_day);
        qtyMorning = itemView.findViewById(R.id.pres_child_qty_morning);
        qtyNight = itemView.findViewById(R.id.pres_child_qty_night);
        courseDays = itemView.findViewById(R.id.pres_child_course_days);

    }
}
