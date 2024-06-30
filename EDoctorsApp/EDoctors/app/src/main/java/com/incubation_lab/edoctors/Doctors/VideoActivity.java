package com.incubation_lab.edoctors.Doctors;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.incubation_lab.edoctors.MainActivity.ui.appointments.AppointmentsViewModel;
import com.incubation_lab.edoctors.Models.AppointmentDataModel;
import com.incubation_lab.edoctors.R;

import org.jitsi.meet.sdk.JitsiMeet;
import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class VideoActivity extends AppCompatActivity {
    private Button joinBtn,closeBtn;
    private ProgressBar closeProgress;
    private String roomId;
    private AppointmentDataModel appointmentDataModel;
    private  Runnable mRunnable;
    private AppointmentsViewModel appointmentsViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        appointmentDataModel= (AppointmentDataModel) getIntent().getSerializableExtra("appointmentData");
        roomId= appointmentDataModel.getLink();


        joinBtn = findViewById(R.id.joinBtn);
        closeBtn = findViewById(R.id.closeBtn);
        closeProgress = findViewById(R.id.closeProgress);
        appointmentsViewModel = new ViewModelProvider(this).get(AppointmentsViewModel.class);


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
                Toast.makeText(getApplicationContext(), StaticDoctorData.loggedInDoctorData.getValue().getBmdc() +appointmentDataModel.getSerial(), Toast.LENGTH_SHORT).show();
                LiveData<String> msg =appointmentsViewModel.updateCurrentSerial(new AppointmentDataModel(appointmentDataModel.getDoctorId(),appointmentDataModel.getSerial()));

                msg.observe(VideoActivity.this, new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if(s.equals("success"))
                            JitsiMeetActivity.launch(VideoActivity.this, options);
                        else if(s.equals("failure")){
                            Toast.makeText(VideoActivity.this, "Something went Wrong! try again", Toast.LENGTH_SHORT).show();
                        }                     
                    }
                });

            }
        });


        Handler mHandler = new Handler();
        mRunnable= new Runnable() {
             @Override
             public void run() {
                 if(closeBtn.isPressed()) {
                     closeProgress.setProgress(closeProgress.getProgress() + 5);
                     mHandler.postDelayed(mRunnable, 10);
                 }
                 else {
                     closeProgress.setProgress(0);
                 }


                }
         };

        closeBtn.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View arg0)
            {
                // Instead of performing a loop here, just call a runnable, do simple press state checking there.
                    mHandler.postDelayed(mRunnable, 10);


                return false;
            }

        });

    }
}