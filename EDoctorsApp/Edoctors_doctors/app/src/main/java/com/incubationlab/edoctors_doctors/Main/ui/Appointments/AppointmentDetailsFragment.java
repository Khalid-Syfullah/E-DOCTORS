package com.incubationlab.edoctors_doctors.Main.ui.Appointments;

import static com.incubationlab.edoctors_doctors.Repository.RemoteAPI.RetroInstance.BASE_URL;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.incubationlab.edoctors_doctors.Main.ui.Appointments.recycler.PrescriptionAdapter;
import com.incubationlab.edoctors_doctors.Models.AppointmentDataModel;
import com.incubationlab.edoctors_doctors.Models.MedicineDataModel;
import com.incubationlab.edoctors_doctors.Models.PrescriptionDataModel;
import com.incubationlab.edoctors_doctors.R;
import com.incubationlab.edoctors_doctors.Repository.RemoteRequestInterface;
import com.squareup.picasso.Picasso;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppointmentDetailsFragment extends Fragment {

    private TextView tvName,tvAge,tvGender,tvSerial;
    private ImageView ivPatientImage;
    private AppointmentDataModel appointmentData;
    private String roomId;
    private Button joinBtn,addMedBtn,uploadBtn;
    private AppointmentsViewModel appointmentsViewModel;
    private EditText etMedName,etQtyMorning,etQtyDay,etQtyNight,etCourseDays;
    private RecyclerView rvPrescription;
    private PrescriptionAdapter prescriptionAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root =inflater.inflate(R.layout.fragment_appointment_details, container, false);

        appointmentsViewModel = new ViewModelProvider(this).get(AppointmentsViewModel.class);

        tvName = root.findViewById(R.id.appointment_details_patient_name);
        tvAge = root.findViewById(R.id.appointment_details_patient_age);
        tvGender = root.findViewById(R.id.appointment_details_patient_gender);
        tvSerial = root.findViewById(R.id.appointment_details_appointment_serial);
        ivPatientImage = root.findViewById(R.id.appointment_details_patient_image);
        etMedName = root.findViewById(R.id.et_med_name);
        etQtyMorning = root.findViewById(R.id.et_med_routine_morning);
        etQtyDay = root.findViewById(R.id.et_med_routine_day);
        etQtyNight = root.findViewById(R.id.et_med_routine_night);
        etCourseDays = root.findViewById(R.id.et_med_routine_course_time);
        addMedBtn = root.findViewById(R.id.btn_add_medicine);
        uploadBtn = root.findViewById(R.id.btn_prescription_upload);
        rvPrescription = root.findViewById(R.id.rv_prescription);

        prescriptionAdapter = new PrescriptionAdapter();
        appointmentData = (AppointmentDataModel) getArguments().getSerializable("appointmentData");
        tvName.setText(appointmentData.getPatientData().getFirstName() + " " +appointmentData.getPatientData().getLastName());
        tvAge.setText(appointmentData.getPatientData().getAge());
        tvGender.setText(appointmentData.getPatientData().getGender());
        tvSerial.setText(appointmentData.getSerial());
        Picasso.get().load(BASE_URL+ "/"+appointmentData.getPatientData().getImageUrl()).into(ivPatientImage);
        roomId= appointmentData.getLink();

        rvPrescription.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        rvPrescription.setAdapter(prescriptionAdapter);


        joinBtn = root.findViewById(R.id.joinBtn);



        URL serverURL;


        try {
            serverURL = new URL("https://meet.jit.si");
            JitsiMeetConferenceOptions defaultOptions =
                    new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(serverURL)
                            .build();
            JitsiMeet.setDefaultConferenceOptions(defaultOptions);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }





        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                        .setRoom(roomId)
                        .setFeatureFlag("invite.enabled", false)
                        .setFeatureFlag("meeting-name.enabled", false)
                        .setFeatureFlag("help.enabled",false)
                        .setFeatureFlag("raise-hand.enabled",false)
                        .setFeatureFlag("recording.enabled",false)
                        .setFeatureFlag("android.screensharing.enabled",false)
                        .setFeatureFlag("live-streaming.enabled",false)
                        .setFeatureFlag("lobby-mode.enabled",false)
                        .setAudioMuted(true)
                        .setVideoMuted(true)
                        .build();
                JitsiMeetActivity.launch(getContext(), options);

                appointmentsViewModel.updateCurrentSerial(new AppointmentDataModel(appointmentData.getDoctorId(), appointmentData.getSerial()), new RemoteRequestInterface() {
                    @Override
                    public void onSuccess(int code, String msg) {
                        Log.d("Update Serial","Updated");
                    }

                    @Override
                    public void onFailure(String msg) {
                        appointmentsViewModel.updateCurrentSerial(new AppointmentDataModel(appointmentData.getDoctorId(),appointmentData.getSerial()),this);
                    }
                });

            }
        });

        addMedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkIfEmpty(new EditText[] {etCourseDays,etMedName,etQtyMorning,etQtyDay,etQtyNight})){
                    addMedicine();
                }
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadPrescription();


            }
        });



        return root;
    }

    private void uploadPrescription() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle("Are you sure?");
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do do my action here
                appointmentsViewModel.addPrescription(new PrescriptionDataModel(appointmentData.getLink(), prescriptionAdapter.getPrescriptionData(), date), new RemoteRequestInterface() {
                    @Override
                    public void onSuccess(int code, String msg) {
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onFailure(String msg) {

                    }
                });
                dialog.dismiss();
            }

        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // I do not need any action here you might
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    private void addMedicine() {

        String medicineName = etMedName.getText().toString();
        String qtyMorning = etQtyMorning.getText().toString();
        String qtyDay = etQtyDay.getText().toString();
        String qtyNight = etQtyNight.getText().toString();
        String courseDays = etCourseDays.getText().toString();

        MedicineDataModel medicineDataModel = new MedicineDataModel(medicineName,qtyMorning,qtyDay,qtyNight,courseDays);
        prescriptionAdapter.addMedicine(medicineDataModel);

    }

    private Boolean checkIfEmpty(EditText[] evs) {
        for (EditText v:
                evs) {
            if(v.getText().toString().isEmpty())
            {
                v.setError("Field Required");
                v.requestFocus();
                return true;
            }
        }

        return false;
    }

}