package com.incubationlab.edoctors_doctors.Main.ui.Appointments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.incubationlab.edoctors_doctors.Main.ui.Appointments.recycler.AppointmentClickHandler;
import com.incubationlab.edoctors_doctors.Main.ui.Appointments.recycler.AppointmentsAdapter;
import com.incubationlab.edoctors_doctors.Models.AppointmentDataModel;
import com.incubationlab.edoctors_doctors.R;
import com.incubationlab.edoctors_doctors.Repository.RemoteRequestInterface;


public class AppointmentsFragment extends Fragment {


    private RecyclerView rvAppointments;
    private AppointmentsAdapter appointmentsAdapter;
    private AppointmentsViewModel appointmentsViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_appointments, container, false);
        rvAppointments = root.findViewById(R.id.rv_appointments);

        rvAppointments.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        appointmentsAdapter = new AppointmentsAdapter(new AppointmentClickHandler() {
            @Override
            public void onclick(AppointmentDataModel appointmentData) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("appointmentData",appointmentData);
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_main).navigate(R.id.action_navigation_appointments_to_navigation_appointment_details,bundle);

            }
        });
        appointmentsViewModel = new ViewModelProvider(this).get(AppointmentsViewModel.class);
        rvAppointments.setAdapter(appointmentsAdapter);

        appointmentsViewModel.fetchAppointments(new RemoteRequestInterface() {
            @Override
            public void onSuccess(int code, String msg) {
                appointmentsAdapter.setData(appointmentsViewModel.getAppointments().getValue());
            }

            @Override
            public void onFailure(String msg) {

            }
        });


        return root;
    }
}