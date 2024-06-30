package com.incubationlab.edoctors_doctors.Main.ui.Appointments.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incubationlab.edoctors_doctors.Models.MedicineDataModel;
import com.incubationlab.edoctors_doctors.R;

import java.util.ArrayList;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionViewHolder> {
    private ArrayList<MedicineDataModel> prescriptionData;

    public PrescriptionAdapter() {
    prescriptionData = new ArrayList<>();
    }

    @NonNull
    @Override
    public PrescriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.prescription_child,parent,false);
        return new PrescriptionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionViewHolder holder, int position) {

        MedicineDataModel prescription = prescriptionData.get(position);
        holder.medName.setText(prescription.getMedicineName());
        holder.qtyMorning.setText(prescription.getQtyMorning());
        holder.qtyDay.setText(prescription.getQtyDay());
        holder.qtyNight.setText(prescription.getQtyNight());
        holder.courseDays.setText(prescription.getDaysOfCourse());

    }

    @Override
    public int getItemCount() {
        return prescriptionData.size();
    }

    public void setPrescriptionData(ArrayList<MedicineDataModel> prescriptionData) {
        this.prescriptionData = prescriptionData;
        notifyDataSetChanged();
    }
    public void addMedicine(MedicineDataModel medicineDataModel){
        for (MedicineDataModel pd:
             prescriptionData) {
            if(pd.getMedicineName().equals(medicineDataModel.getMedicineName())){
                return;
            }
        }
        prescriptionData.add(medicineDataModel);

        notifyDataSetChanged();
    }

    public ArrayList<MedicineDataModel> getPrescriptionData() {
        return prescriptionData;
    }
}
