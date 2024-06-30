package com.incubation_lab.edoctors.Splash;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.progressindicator.LinearProgressIndicator;
import com.incubation_lab.edoctors.Login.LoginActivity;
import com.incubation_lab.edoctors.Login.ui.LoginViewModel;
import com.incubation_lab.edoctors.MainActivity.MainActivity;
import com.incubation_lab.edoctors.R;
import com.incubation_lab.edoctors.Splash.ui.ViewPagerAdapter;

import static com.incubation_lab.edoctors.Repository.UserRepository.ACCESS_TOKEN;
import static com.incubation_lab.edoctors.Repository.UserRepository.LOGIN_SHARED_PREFS;

public class SplashActivity extends AppCompatActivity {

    private ImageView logo;
    private LinearProgressIndicator indicator;
    private  ViewPager2 splashViewPager;
    private ConstraintLayout container;
    private  LoginViewModel loginViewModel;
    private  ObjectAnimator progressAnimator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        logo = findViewById(R.id.splash_logo);
        indicator=findViewById(R.id.splash_progress_indicator);
        splashViewPager = findViewById(R.id.splash_viewpager);
        container=findViewById(R.id.splash_container);
        AlphaAnimation animation = new AlphaAnimation(.2f,1.0f);
        animation.setDuration(1000);
        logo.startAnimation(animation);
        loginViewModel= new ViewModelProvider(this).get(LoginViewModel.class);

        SharedPreferences sharedPreferences = getSharedPreferences(LOGIN_SHARED_PREFS, MODE_PRIVATE);
        progressAnimator = ObjectAnimator.ofInt(indicator,"progress",0,100);
        progressAnimator.setDuration(1000);
        progressAnimator.start();
        String token;
        if (sharedPreferences.contains(ACCESS_TOKEN) ) {
            token = sharedPreferences.getString(ACCESS_TOKEN, "");
            loginViewModel.validateToken(token);
            loginViewModel.getLoginStatus().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if(s.equals("logged in")){
                        startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        finish();
                    }
                    else if(s.equals("login required")){
                        Toast.makeText(SplashActivity.this, "Session Expired", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                        finish();
                    }
                }
            });

        }
        else {

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
                splashViewPager.setAdapter(new ViewPagerAdapter(SplashActivity.this));
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