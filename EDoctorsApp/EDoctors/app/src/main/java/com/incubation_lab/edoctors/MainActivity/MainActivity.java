package com.incubation_lab.edoctors.MainActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.incubation_lab.edoctors.Login.ui.LoginViewModel;
import com.incubation_lab.edoctors.R;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import org.jetbrains.annotations.NotNull;

import static com.incubation_lab.edoctors.Repository.Remote.RetroInstance.BASE_URL;

public class MainActivity extends AppCompatActivity {

    private ImageView drawerBtn;
    private DrawerLayout mDrawerLayout;
    private ImageView profileIcon;
    private LoginViewModel loginViewModel;
    private  NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavigationView navigationView = findViewById(R.id.nav_drawer_view);
        drawerBtn = findViewById(R.id.drawer_btn);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        profileIcon= findViewById(R.id.profile_icon);
        loginViewModel= new ViewModelProvider(this).get(LoginViewModel.class);

        Picasso.get().load(BASE_URL +"/"+loginViewModel.getLoggedInUser().getValue().getImageUrl()).placeholder(R.drawable.account).into(profileIcon);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
         navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupWithNavController(navigationView, navController);


        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull @NotNull NavController controller, @NonNull @NotNull NavDestination destination, @Nullable @org.jetbrains.annotations.Nullable Bundle arguments) {
                navView.getMenu().setGroupCheckable(0, (destination.getId() != R.id.navigation_med_reminder)&& (destination.getId() != R.id.navigation_profile), true);
            }
        });


        drawerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mDrawerLayout.isDrawerOpen(GravityCompat.START)){
                    mDrawerLayout.open();
                }
            }
        });

        profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentDestination=navController.getCurrentDestination().getId();
                if(currentDestination== R.id.navigation_home){
                    navController.navigate(R.id.action_navigation_home_to_navigation_profile);
                }
                if(currentDestination== R.id.navigation_doctors){
                    navController.navigate(R.id.action_navigation_doctors_to_navigation_profile);
                }
                if(currentDestination== R.id.navigation_appointments){
                    navController.navigate(R.id.action_navigation_notifications_to_navigation_profile);
                }
                if(currentDestination== R.id.navigation_med_reminder){
                    navController.navigate(R.id.action_navigation_med_reminder_to_navigation_profile);
                }
                if(currentDestination== R.id.navigation_health_blogs){
                    navController.navigate(R.id.action_navigation_health_blogs_to_navigation_profile);
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