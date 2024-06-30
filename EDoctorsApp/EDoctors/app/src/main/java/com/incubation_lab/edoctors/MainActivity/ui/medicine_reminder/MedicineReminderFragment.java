package com.incubation_lab.edoctors.MainActivity.ui.medicine_reminder;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.incubation_lab.edoctors.R;

import java.util.Calendar;

public class MedicineReminderFragment extends Fragment {

    Button setAlarm,selectTime,cancelAlarm;
    TextView selectedTime;
    private MaterialTimePicker picker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View root = inflater.inflate(R.layout.fragment_medicine_reminder, container, false);
         setAlarm = root.findViewById(R.id.setAlarm);
         selectTime = root.findViewById(R.id.select_time);
         cancelAlarm = root.findViewById(R.id.cancel_alarm);
        selectedTime = root.findViewById(R.id.alarm_time);
         createNotificationChannel();


         selectTime.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 showTimePicker();

             }
         });


         setAlarm.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 setAlarm();

             }
         });

         cancelAlarm.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 cancelAlarm();

             }
         });



         return root;
    }

    private void cancelAlarm() {
        Intent intent = new Intent(getContext(),MedReminderBrdCstReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);

        if(alarmManager == null){
            alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        }

        alarmManager.cancel(pendingIntent);
    }

    private void setAlarm() {
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(getContext(),MedReminderBrdCstReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(getContext(),0,intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);


    }

    private void showTimePicker() {
        picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(12).setMinute(0)
                .setTitleText("Select Time")
                .build();
        picker.show(getChildFragmentManager(),"med_reminder");
        picker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(picker.getHour()>12){
                    selectedTime.setText(String.valueOf(picker.getHour()-12) + " : " + (picker.getMinute()<10?"0":"")+picker.getMinute() +" PM");
                }else {
                    selectedTime.setText(picker.getHour()+ " : " + (picker.getMinute()<10?"0":"")+picker.getMinute() + " AM");

                }
                calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,picker.getHour());
                calendar.set(Calendar.MINUTE,picker.getMinute());
                calendar.set(Calendar.SECOND,0);
                calendar.set(Calendar.MILLISECOND,0);
            }
        });

    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "medReminderChannel";
            String description = "Alarm channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("med_reminder",name,importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}