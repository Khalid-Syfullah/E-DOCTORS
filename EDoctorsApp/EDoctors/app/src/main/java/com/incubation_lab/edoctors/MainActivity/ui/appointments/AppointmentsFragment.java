package com.incubation_lab.edoctors.MainActivity.ui.appointments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.incubation_lab.edoctors.Doctors.AppointmentListActivity;
import com.incubation_lab.edoctors.Doctors.VideoActivity;
import com.incubation_lab.edoctors.Login.ui.LoginViewModel;
import com.incubation_lab.edoctors.MainActivity.ui.appointments.recycler.AppointmentsAdapter;
import com.incubation_lab.edoctors.Models.AppointmentDataModel;
import com.incubation_lab.edoctors.R;
import com.incubation_lab.edoctors.StaticData;

import java.util.ArrayList;

public class AppointmentsFragment extends Fragment {

    private AppointmentsViewModel appointmentsViewModel;
    private RecyclerView rvAppointments;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        appointmentsViewModel =
                new ViewModelProvider(this).get(AppointmentsViewModel.class);
        LoginViewModel loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        View root = inflater.inflate(R.layout.fragment_appointments, container, false);

        rvAppointments = root.findViewById(R.id.rv_appointments);
        rvAppointments.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        AppointmentsAdapter appointmentsAdapter = new AppointmentsAdapter("patient",appointmentData -> {
//            Intent intent = new Intent(getContext(), VideoActivity.class);
//            intent.putExtra("appointmentData",appointmentData);
//
//            startActivity(intent);
            Bundle bundle = new Bundle();
            bundle.putSerializable("appointmentData",appointmentData);
            Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_navigation_appointments_to_navigation_appointment_details,bundle);


        });
        rvAppointments.setAdapter(appointmentsAdapter);

        appointmentsViewModel.getPatientAppointmentList(loginViewModel.getLoggedInUser().getValue().getPhone()).observe(getViewLifecycleOwner(), new Observer<ArrayList<AppointmentDataModel>>() {
            @Override
            public void onChanged(ArrayList<AppointmentDataModel> appointmentDataModels) {
                appointmentsAdapter.setAppointmentData(appointmentDataModels);
                Toast.makeText(getContext(),""+ appointmentDataModels.size(), Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }
}