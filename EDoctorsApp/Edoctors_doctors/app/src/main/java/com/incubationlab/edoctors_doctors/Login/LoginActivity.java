package com.incubationlab.edoctors_doctors.Login;
import android.content.res.ColorStateList;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import com.incubationlab.edoctors_doctors.R;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, NavController.OnDestinationChangedListener {

    private TextView loginTab, registerTab;
    private NavController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginTab = findViewById(R.id.login_tab);
        registerTab = findViewById(R.id.register_tab);
        controller =Navigation.findNavController(this, R.id.nav_host_fragment_login);
        loginTab.setOnClickListener(this);
        registerTab.setOnClickListener(this);

        controller.addOnDestinationChangedListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.login_tab){
            if(controller.getCurrentDestination().getId() == R.id.nav_registration ){
                controller.navigate(R.id.action_nav_registration_to_nav_login);
                changeTabBackground(loginTab,registerTab);
            }
            else if( controller.getCurrentDestination().getId() == R.id.nav_mobile_verification){
                controller.navigate(R.id.action_nav_mobile_verification_to_nav_login);
                changeTabBackground(loginTab,registerTab);
            }
        }
        else if(v.getId() == R.id.register_tab){
            if(controller.getCurrentDestination().getId() == R.id.nav_login){
                controller.navigate(R.id.action_nav_login_to_nav_mobile_verification);
            }
            else if(controller.getCurrentDestination().getId() == R.id.nav_reset_pass){
                controller.navigate(R.id.action_nav_reset_pass_to_nav_mobile_verification);
            }

        }

    }

    private void changeTabBackground(TextView tabSelected, TextView tabNotSelected) {
        tabSelected.setTextAppearance(R.style.CustomTabSelected);
        tabNotSelected.setTextAppearance(R.style.CustomTabNotSelected);
        tabSelected.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.white)));
        tabNotSelected.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_primary)));
    }

    @Override
    public void onDestinationChanged(@NonNull NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
        switch (destination.getId()) {
            case R.id.nav_login:
            case R.id.nav_reset_pass:
                changeTabBackground(loginTab, registerTab);
                break;
            case R.id.nav_registration:
            case R.id.nav_mobile_verification:
                changeTabBackground(registerTab,loginTab);
                break;
        }


    }
}