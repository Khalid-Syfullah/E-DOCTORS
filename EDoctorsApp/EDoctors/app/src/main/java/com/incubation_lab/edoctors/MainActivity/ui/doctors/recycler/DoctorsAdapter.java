package com.incubation_lab.edoctors.MainActivity.ui.doctors.recycler;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;


import com.incubation_lab.edoctors.Models.DoctorDataModel;
import com.incubation_lab.edoctors.R;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;

import static com.incubation_lab.edoctors.Repository.Remote.RetroInstance.BASE_URL;
import static com.incubation_lab.edoctors.StaticData.DOCTOR_BUNDLE_KEY;

public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsViewHolder> {

    private ArrayList<DoctorDataModel> allDoctors, vDoctors;


    public DoctorsAdapter() {
        allDoctors = new ArrayList<>();
        vDoctors = new ArrayList<>();

    }


    @NonNull
    @NotNull
    @Override
    public DoctorsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors_child, parent, false);


        return new DoctorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull DoctorsViewHolder holder, int position) {

        holder.name.setText(vDoctors.get(position).getName());
        holder.speciality.setText(vDoctors.get(position).getSpeciality());
        holder.currentDesignation.setText(vDoctors.get(position).getCurrentDesignation());
        holder.feeText.setText(vDoctors.get(position).getFee() + " Tk.");
        Picasso.get().load( vDoctors.get(position).getImageUrl()).placeholder(R.drawable.icon_doctor).into(holder.doctorImage);
        holder.ratingBar.setRating((float) (Float.parseFloat(vDoctors.get(position).getRating())/5.0));
        holder.tvRating.setText(""+allDoctors.get(position).getRating());

        Bundle bundle = new Bundle();
        bundle.putSerializable(DOCTOR_BUNDLE_KEY, allDoctors.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.createNavigateOnClickListener(R.id.navigation_doctor_profile, bundle).onClick(holder.itemView);

            }
        });


    }

    @Override
    public int getItemCount() {
        return vDoctors.size();
    }

    public void setAllDoctors(ArrayList<DoctorDataModel> allDoctors) {
        this.allDoctors = allDoctors;
        this.vDoctors =allDoctors;
        notifyDataSetChanged();
    }

    public void filter(String dept, String docName) {
        vDoctors=new ArrayList<>();

        for (int i = 0; i < allDoctors.size(); i++) {
            DoctorDataModel doctorData = allDoctors.get(i);
//            Log.d("docdata",vDoctors.toString());
            if (doctorData.getSpeciality().equalsIgnoreCase(dept) && doctorData.getName().toLowerCase().contains(docName.toLowerCase())) {
                vDoctors.add(doctorData);


            }
        }
        notifyDataSetChanged();
    }
}
