package com.incubationlab.edoctors_doctors.Main.ui.Settings;

import static com.incubationlab.edoctors_doctors.StaticData.LoggedInDoctorData;

import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.incubationlab.edoctors_doctors.R;
import com.incubationlab.edoctors_doctors.Repository.SettingsRepository;


public class SettingsFragment extends Fragment {


    private SwitchCompat stat;
    private SettingsRepository settingsRepository;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_settings, container, false);
        stat = root.findViewById(R.id.switch_status);
        settingsRepository =new SettingsRepository();
        stat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    settingsRepository.setStatus(LoggedInDoctorData.getValue());
                }
                else{
                    settingsRepository.unSetStatus(LoggedInDoctorData.getValue());
                }
            }
        });



        return root;
    }
}