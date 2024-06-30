package com.incubationlab.edoctors_doctors.Splash;


import static com.incubationlab.edoctors_doctors.StaticData.DOCTOR_ACCESS_TOKEN;
import static com.incubationlab.edoctors_doctors.StaticData.DOCTOR_LOGIN_SHARED_PREFS;
import static com.incubationlab.edoctors_doctors.StaticData.LoggedInDoctorData;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.google.android.material.snackbar.Snackbar;
import com.incubationlab.edoctors_doctors.Login.LoginActivity;
import com.incubationlab.edoctors_doctors.Login.ui.LoginViewModel;
import com.incubationlab.edoctors_doctors.Main.MainActivity;
import com.incubationlab.edoctors_doctors.R;
import com.incubationlab.edoctors_doctors.Repository.RemoteRequestInterface;


public class SplashActivity extends AppCompatActivity {

    private ImageView logo;
    private LinearProgressIndicator indicator;
    private ViewPager2 splashViewPager;
    private ConstraintLayout container;
    private LoginViewModel loginViewModel;
    private ObjectAnimator progressAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.splash_logo);
        indicator = findViewById(R.id.splash_progress_indicator);
        splashViewPager = findViewById(R.id.splash_viewpager);
        container = findViewById(R.id.splash_container);
        AlphaAnimation animation = new AlphaAnimation(.2f, 1.0f);
        animation.setDuration(1000);
        logo.startAnimation(animation);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        SharedPreferences sharedPreferences = getSharedPreferences(DOCTOR_LOGIN_SHARED_PREFS, MODE_PRIVATE);
        progressAnimator = ObjectAnimator.ofInt(indicator, "progress", 0, 100);
        progressAnimator.setDuration(1000);
        progressAnimator.start();
        String token;
        if (sharedPreferences.contains(DOCTOR_ACCESS_TOKEN)) {
            token = sharedPreferences.getString(DOCTOR_ACCESS_TOKEN, "");
            loginViewModel.validateToken(token, new RemoteRequestInterface() {
                @Override
                public void onSuccess(int code, String msg) {
                    if (code == 202) {
                        Toast.makeText(SplashActivity.this, "Welcome " + LoggedInDoctorData.getValue().getLastName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SplashActivity.this, "Session Expired", Toast.LENGTH_SHORT).show();
                        animateToLogin();
                    }
                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(SplashActivity.this, "Something Went Wrong! Check Your Connection", Toast.LENGTH_SHORT).show();
                    animateToLogin();

                }
            });


        } else {

            animateToLogin();


        }

    }

    private void animateToLogin() {
        progressAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}