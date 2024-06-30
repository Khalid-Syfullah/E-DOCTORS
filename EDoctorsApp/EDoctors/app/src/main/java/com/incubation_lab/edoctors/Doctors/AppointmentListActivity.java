package com.incubation_lab.edoctors.Doctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.incubation_lab.edoctors.Login.ui.LoginViewModel;
import com.incubation_lab.edoctors.MainActivity.ui.appointments.AppointmentsViewModel;
import com.incubation_lab.edoctors.MainActivity.ui.appointments.recycler.AppointmentsAdapter;
import com.incubation_lab.edoctors.Models.AppointmentDataModel;
import com.incubation_lab.edoctors.R;

import java.util.ArrayList;

import static com.incubation_lab.edoctors.Doctors.StaticDoctorData.loggedInDoctorData;

public class AppointmentListActivity extends AppCompatActivity {
    private AppointmentsViewModel appointmentsViewModel;
    private RecyclerView rvAppointments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_appointments);
        appointmentsViewModel =
                new ViewModelProvider(this).get(AppointmentsViewModel.class);


        rvAppointments = findViewById(R.id.rv_appointments);
        rvAppointments.setLayoutManager(new LinearLayoutManager(this,RecyclerView.VERTICAL,false));
        AppointmentsAdapter appointmentsAdapter = new AppointmentsAdapter("doctor",appointmentData -> {
            Intent intent = new Intent(AppointmentListActivity.this,VideoActivity.class);
            intent.putExtra("appointmentData",appointmentData);

            startActivity(intent);
        });
        rvAppointments.setAdapter(appointmentsAdapter);

        appointmentsViewModel.getDoctorAppointmentList(loggedInDoctorData.getValue().getBmdc()).observe(this, new Observer<ArrayList<AppointmentDataModel>>() {
            @Override
            public void onChanged(ArrayList<AppointmentDataModel> appointmentDataModels) {
                appointmentsAdapter.setAppointmentData(appointmentDataModels);
            }
        });



    }
}