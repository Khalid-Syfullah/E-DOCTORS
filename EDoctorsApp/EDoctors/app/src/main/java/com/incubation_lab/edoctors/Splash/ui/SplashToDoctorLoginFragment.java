package com.incubation_lab.edoctors.Splash.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.incubation_lab.edoctors.Doctors.DoctorLoginActivity;
import com.incubation_lab.edoctors.R;

public class SplashToDoctorLoginFragment extends Fragment {
    private Button loginBtn;
    public static Fragment newInstance() {
        return new SplashToDoctorLoginFragment();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_splash_to_doctor_login, container, false);
        loginBtn = root.findViewById(R.id.doctor_login_register_btn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), DoctorLoginActivity.class));
            }
        });


        return root;
    }
}
