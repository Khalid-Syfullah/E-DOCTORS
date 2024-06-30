package com.incubation_lab.edoctors.MainActivity.ui.appointments;

import static com.incubation_lab.edoctors.Repository.Remote.RetroInstance.BASE_URL;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.incubation_lab.edoctors.MainActivity.ui.appointments.recycler.PrescriptionAdapter;
import com.incubation_lab.edoctors.Models.AppointmentDataModel;
import com.incubation_lab.edoctors.Models.PrescriptionDataModel;
import com.incubation_lab.edoctors.R;
import com.incubation_lab.edoctors.Repository.Remote.PrescriptionGetterInterface;
import com.incubation_lab.edoctors.Repository.Remote.RemoteRequestInterface;
import com.squareup.picasso.Picasso;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class AppointmentDetailsFragment extends Fragment {

    private ProgressBar liveProgressBar;
    private ObjectAnimator progressAnimator;
    private int animCycleCount=0;
    private Button joinBtn,addReviewBtn;
    private String roomId;
    private RecyclerView rvPrescription;
    private TextView doctorName,doctorDesignation,doctorQualifications, tvCurrentSerial,tvUserSerial;
    private ImageView doctorImg;
    private AppointmentDataModel appointmentDataModel;
    private AppointmentsViewModel appointmentsViewModel;
    private PrescriptionAdapter prescriptionAdapter;

    private Handler handler = new Handler();
    private Runnable runnable;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = LayoutInflater.from(getContext()).inflate(R.layout.fragment_appointment_details,container,false);
        liveProgressBar = root.findViewById(R.id.mf_progress_bar);
        forwardAnimation();
        appointmentsViewModel = new ViewModelProvider(this).get(AppointmentsViewModel.class);
        prescriptionAdapter = new PrescriptionAdapter();

        joinBtn = root.findViewById(R.id.joinBtn);
        addReviewBtn = root.findViewById(R.id.btn_add_review);
        doctorName = root.findViewById(R.id.tv_chamber_doctor_name);
        doctorDesignation = root.findViewById(R.id.tv_chamber_doctor_designation);
        doctorQualifications = root.findViewById(R.id.tv_chamber_doctor_qualification);
        doctorImg = root.findViewById(R.id.iv_chamber_doctor_img);
        tvCurrentSerial = root.findViewById(R.id.tv_chamber_current_serial);
        tvUserSerial = root.findViewById(R.id.tv_chamber_user_serial);
        rvPrescription = root.findViewById(R.id.rv_prescription);

        appointmentDataModel= (AppointmentDataModel) getArguments().getSerializable("appointmentData");

        rvPrescription.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        rvPrescription.setAdapter(prescriptionAdapter);

        doctorName.setText(appointmentDataModel.getDoctorDataModel().getName());
        doctorDesignation.setText(appointmentDataModel.getDoctorDataModel().getCurrentDesignation());
        doctorQualifications.setText(appointmentDataModel.getDoctorDataModel().getQualifications());
        tvUserSerial.setText(appointmentDataModel.getSerial());
        getPrescription(appointmentDataModel.getLink());

        if(!appointmentDataModel.getDoctorDataModel().getImageUrl().equals("not set"))
            Picasso.get().load( BASE_URL +"/" + appointmentDataModel.getDoctorDataModel().getImageUrl()).into(doctorImg);
        setupConference();

        joinBtn.setEnabled(false);


        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinConference();
            }
        });

        handler.postDelayed(runnable =new Runnable() {
            @Override
            public void run() {
                setCurrentSerial();


            }
        },10);
        addReviewBtn.setVisibility(appointmentDataModel.getReview().equals("not reviewed")?View.VISIBLE:View.GONE);

        addReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

                LayoutInflater inflater = LayoutInflater.from(getContext());
                View reviewAlert = inflater.inflate(R.layout.add_rating_alert, null);
                EditText etReview = reviewAlert.findViewById(R.id.et_review);
                RatingBar ratingBar = reviewAlert.findViewById(R.id.review_ratingBar);
                Button submitBtn = reviewAlert.findViewById(R.id.review_alert_submit_btn);

                builder.setView(reviewAlert);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                submitBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(etReview.getText().toString().isEmpty()){
                            etReview.setError("write a review");
                            return;
                        }
                        appointmentDataModel.setReview(etReview.getText().toString());
                        appointmentDataModel.setRating(String.valueOf(ratingBar.getRating()));
                        appointmentsViewModel.addReview(appointmentDataModel, new RemoteRequestInterface() {
                            @Override
                            public void onSuccess(int code, String msg) {
                                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }

                            @Override
                            public void onFailure(String msg) {
                                Toast.makeText(getContext(), "Something Went wrong", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                });

            }
        });




        return root;
    }

    private void getPrescription(String link) {
        appointmentsViewModel.getPrescription(link, new PrescriptionGetterInterface() {
            @Override
            public void onSuccess(PrescriptionDataModel prescriptionDataModel) {

                prescriptionAdapter.setMedicineData(prescriptionDataModel.getMedicineDataModels());
            }

            @Override
            public void onFailure() {
                Toast.makeText(getContext(), "failed", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void reverseAnimation(){
        animCycleCount++;
        liveProgressBar.setRotation(animCycleCount*180);
        if (animCycleCount ==2)
        {
            animCycleCount=0;
        }
        progressAnimator = ObjectAnimator.ofInt(liveProgressBar,"progress",100,0);
        progressAnimator.start();
        progressAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

                forwardAnimation();


            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void forwardAnimation(){
        progressAnimator = ObjectAnimator.ofInt(liveProgressBar,"progress",0,100);
        progressAnimator.setDuration(1000);
        progressAnimator.start();

        progressAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {


            }

            @Override
            public void onAnimationEnd(Animator animation) {



                reverseAnimation();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    private void setupConference(){

        roomId= appointmentDataModel.getLink();




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





    }
    private void joinConference(){
        JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                .setRoom(roomId)
                .setFeatureFlag("invite.enabled", false)
                .setFeatureFlag("meeting-name.enabled", false)
                .setFeatureFlag("help.enabled",false)
                .setAudioMuted(true)
                .setVideoMuted(true)
                .build();

        JitsiMeetActivity.launch(getContext(), options);
    }

    private void setCurrentSerial(){
        try {
            appointmentsViewModel.getCurrentSerial(appointmentDataModel.getDoctorId()).observe(getViewLifecycleOwner(), new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    tvCurrentSerial.setText(s);
                    int distance = Integer.parseInt(appointmentDataModel.getSerial())-Integer.parseInt(s);
                    if(distance >=0 && distance <3 ){
                        joinBtn.setEnabled(true);
                    }
                    else {
                        joinBtn.setEnabled(false);
                    }
                }
            });
            handler.postDelayed(runnable,10000);
        } catch (Exception e){

            e.printStackTrace();

        }

    }



}
