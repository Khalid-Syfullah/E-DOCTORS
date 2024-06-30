package com.incubationlab.edoctors_doctors.Main;

import static com.incubationlab.edoctors_doctors.Repository.RemoteAPI.RetroInstance.BASE_URL;
import static com.incubationlab.edoctors_doctors.StaticData.LoggedInDoctorData;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.navigation.NavigationView;
import com.incubationlab.edoctors_doctors.R;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private ImageView drawerBtn;
    private DrawerLayout mDrawerLayout;
    private ImageView profileIcon;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerBtn = findViewById(R.id.drawer_btn);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        profileIcon = findViewById(R.id.profile_icon);
        Picasso.get().load(BASE_URL + "/" + LoggedInDoctorData.getValue().getImageUrl()).placeholder(R.drawable.account).into(profileIcon);


        NavigationView navigationView = findViewById(R.id.nav_view);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment_main);
        NavigationUI.setupWithNavController(navigationView, navController);

        drawerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    mDrawerLayout.open();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (navController.getCurrentDestination().getId() == R.id.navigation_home)
            super.onBackPressed();
        else
            navController.navigateUp();
    }
}