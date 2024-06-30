package com.incubation_lab.edoctors.Login;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.incubation_lab.edoctors.Login.ui.LoginFragment;
import com.incubation_lab.edoctors.Login.ui.MobileVerificationFragment;
import com.incubation_lab.edoctors.Login.ui.RegisterFragment;
import com.incubation_lab.edoctors.R;

import static com.incubation_lab.edoctors.StaticData.LoginCurrentFragment;

public class LoginActivity extends AppCompatActivity {

    private TextView login,register;
    private FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login= findViewById(R.id.login_tab);
        register = findViewById(R.id.register_tab);
        frameLayout = findViewById(R.id.login_frame);


        setFragment(new LoginFragment());

        LoginCurrentFragment.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                if(integer==1){
                    login.setTextAppearance(R.style.CustomTabSelected);
                    register.setTextAppearance(R.style.CustomTabNotSelected);
                    login.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.white)));
                    register.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_primary)));

                }
                else {
                    register.setTextAppearance(R.style.CustomTabSelected);
                    login.setTextAppearance(R.style.CustomTabNotSelected);
                    register.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.white)));
                    login.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_primary)));
                }
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.login_frame);

                if(currentFragment  instanceof LoginFragment){
                    return;
                }

                login.setTextAppearance(R.style.CustomTabSelected);
                register.setTextAppearance(R.style.CustomTabNotSelected);
                login.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.white)));
                register.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_primary)));

                setFragment(new LoginFragment());
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.login_frame);

                if(currentFragment  instanceof RegisterFragment|| currentFragment instanceof MobileVerificationFragment){
                    return;
                }
                register.setTextAppearance(R.style.CustomTabSelected);
                login.setTextAppearance(R.style.CustomTabNotSelected);
                register.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.white)));
                login.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_primary)));
                setFragment(new MobileVerificationFragment());
            }
        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.login_frame);
        if(currentFragment  instanceof RegisterFragment|| currentFragment instanceof MobileVerificationFragment){
            login.setTextAppearance(R.style.CustomTabSelected);
            register.setTextAppearance(R.style.CustomTabNotSelected);
            login.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.white)));
            register.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.red_primary)));

            setFragment(new LoginFragment());
        }
        else {
            super.onBackPressed();
        }
    }
    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.login_frame,fragment);
        if(fragment instanceof RegisterFragment){
            LoginCurrentFragment.setValue(2);
        }
        else if(fragment instanceof LoginFragment){
            LoginCurrentFragment.setValue(1);
        }



        fragmentTransaction.commit();
    }

}