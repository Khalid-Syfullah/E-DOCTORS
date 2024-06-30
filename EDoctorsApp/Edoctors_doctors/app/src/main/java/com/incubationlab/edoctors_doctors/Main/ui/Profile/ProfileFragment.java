package com.incubationlab.edoctors_doctors.Main.ui.Profile;

import static com.incubationlab.edoctors_doctors.StaticData.LoggedInDoctorData;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.incubationlab.edoctors_doctors.Models.DoctorDataModel;
import com.incubationlab.edoctors_doctors.R;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment {

    private TextView tvFirstName,tvLastName,tvBmdc,tvSpeciality,tvDesignation,tvQualifications,
                    tvBio,tvPhone,tvFullName;
    private ImageView ivProfileImage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        tvFirstName = root.findViewById(R.id.tvProfileFirstName);
        tvLastName = root.findViewById(R.id.tvProfileLastName);
        tvBmdc = root.findViewById(R.id.tvProfileBmdc);
        tvSpeciality = root.findViewById(R.id.tvProfileSpeciality);
        tvDesignation = root.findViewById(R.id.tvProfileDesignation);
        tvQualifications = root.findViewById(R.id.tvProfileQualifications);
        tvBio = root.findViewById(R.id.tvProfileBio);
        tvPhone = root.findViewById(R.id.tvProfilePhone);
        tvFullName = root.findViewById(R.id.profile_username);
        ivProfileImage = root.findViewById(R.id.profile_image);
        setProfileData();
        String imageUrl = LoggedInDoctorData.getValue().getImageUrl();
        if(!imageUrl.equals("not set")) {
            Picasso.get().load(LoggedInDoctorData.getValue().getImageUrl()).into(ivProfileImage);
        }

        LoggedInDoctorData.observe(getViewLifecycleOwner(), new Observer<DoctorDataModel>() {
                    @Override
                    public void onChanged(DoctorDataModel doctorDataModel) {
                        setProfileData();
                    }
                }
        );


        return root;
    }

    private void setProfileData() {
        DoctorDataModel doctorData = LoggedInDoctorData.getValue();
        tvFirstName.setText(doctorData.getFirstName());
        tvLastName.setText(doctorData.getLastName());
        tvBmdc.setText(doctorData.getDoctorId());
        tvSpeciality.setText(doctorData.getSpeciality());
        tvDesignation.setText(doctorData.getDesignation());
        tvQualifications.setText(doctorData.getQualifications());
        tvBio.setText(doctorData.getBio());
        tvPhone.setText(doctorData.getPhone());
        tvFullName.setText(doctorData.getFirstName() + " " +doctorData.getLastName());

    }
}