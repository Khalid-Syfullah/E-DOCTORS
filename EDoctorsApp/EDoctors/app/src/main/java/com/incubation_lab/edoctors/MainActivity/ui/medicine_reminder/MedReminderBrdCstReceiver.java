package com.incubation_lab.edoctors.MainActivity.ui.medicine_reminder;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.CountDownTimer;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.incubation_lab.edoctors.MainActivity.MainActivity;
import com.incubation_lab.edoctors.R;

public class MedReminderBrdCstReceiver extends BroadcastReceiver {
    MediaPlayer mRingtoneLooper;
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i =new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,i,0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"med_reminder")
                .setSmallIcon(R.drawable.logo_red)
                .setContentTitle("Medicine Reminder")
                .setContentText("Take your Medicine")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(1234,builder.build());


        try {

            Uri ringtoneAlert =  RingtoneManager.
                    getDefaultUri(RingtoneManager.TYPE_ALARM);

            mRingtoneLooper = new MediaPlayer();
            mRingtoneLooper.setDataSource(context, ringtoneAlert);

            final AudioManager audioRingtoneManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

            if (audioRingtoneManager.getStreamVolume(AudioManager.STREAM_RING) != 0) {

                mRingtoneLooper.setAudioStreamType(AudioManager.STREAM_RING);
                mRingtoneLooper.setLooping(true);
                mRingtoneLooper.prepare();
                mRingtoneLooper.start();

                //start custom countdown timer for 60 seconds, counts every second down
                //counting every second down is not necessary, You could even set every 5 seconds or whatever You want

                RingtoneStopper stopper = new RingtoneStopper(30000,1000);
                stopper.start();
            }
        } catch(Exception e) {

            //do some message to user if some error occurs
        }

        //define a countdown timer for 60 seconds that stops the MediaPlayer




    }
    public class RingtoneStopper extends CountDownTimer {

        public RingtoneStopper(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            mRingtoneLooper.stop();
        }

        @Override
        public void onTick(long millisUntilFinished) {
            //need nothing to do on tick events
        }
    }
}
