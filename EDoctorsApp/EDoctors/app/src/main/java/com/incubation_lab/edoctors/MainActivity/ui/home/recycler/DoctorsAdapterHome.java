package com.incubation_lab.edoctors.MainActivity.ui.home.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incubation_lab.edoctors.Models.DoctorDataModel;
import com.incubation_lab.edoctors.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class DoctorsAdapterHome extends RecyclerView.Adapter<DoctorsViewHolder> {

    private OnDoctorClickListenerHome onDoctorClickListenerHome;

    public DoctorsAdapterHome(OnDoctorClickListenerHome onDoctorClickListenerHome) {
        this.onDoctorClickListenerHome = onDoctorClickListenerHome;
    }

    ArrayList<DoctorDataModel> doctorsList=new ArrayList<>();


    @NonNull
    @Override
    public DoctorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors_child_horizontal,parent,false);
        return new DoctorsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DoctorsViewHolder holder, int position) {
        DoctorDataModel doctorData = doctorsList.get(position);
        holder.docDept.setText(doctorData.getSpeciality());
        holder.docName.setText(doctorData.getName());
        Picasso.get().load(doctorData.getImageUrl()).placeholder(R.drawable.icon_doctor).into(holder.docImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onDoctorClickListenerHome.onclick(doctorData);
            }
        });

    }

    @Override
    public int getItemCount() {
        return Math.min(doctorsList.size(), 5);
    }
    public void setDoctorsList(ArrayList<DoctorDataModel> doctorsList) {
        this.doctorsList = doctorsList;
        notifyDataSetChanged();
    }
}

class DoctorsViewHolder extends RecyclerView.ViewHolder {
    ImageView docImage;
    TextView docName,docDept;

    public DoctorsViewHolder(@NonNull View itemView) {
        super(itemView);
        docImage = itemView.findViewById(R.id.iv_docImg);
        docName = itemView.findViewById(R.id.tv_docName);
        docDept = itemView.findViewById(R.id.tv_docDept);
    }
}

