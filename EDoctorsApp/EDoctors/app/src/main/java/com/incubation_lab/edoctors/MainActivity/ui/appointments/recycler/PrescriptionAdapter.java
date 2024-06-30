package com.incubation_lab.edoctors.MainActivity.ui.appointments.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.incubation_lab.edoctors.Models.MedicineDataModel;
import com.incubation_lab.edoctors.R;

import java.util.ArrayList;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionViewHolder> {
    private ArrayList<MedicineDataModel> medicineData;

    public PrescriptionAdapter() {
    medicineData = new ArrayList<>();
    }

    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_child,parent,false);
        return new PrescriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, int position) {

        MedicineDataModel prescription = medicineData.get(position);
        holder.medName.setText(prescription.getMedicineName());
        holder.qtyMorning.setText(prescription.getQtyMorning());
        holder.qtyDay.setText(prescription.getQtyDay());
        holder.qtyNight.setText(prescription.getQtyNight());
        holder.courseDays.setText(prescription.getDaysOfCourse());

    }

    @Override
    public int getItemCount() {
        return medicineData.size();
    }

    public void setMedicineData(ArrayList<MedicineDataModel> medicineData) {
        this.medicineData = medicineData;


        notifyDataSetChanged();
    }

}
