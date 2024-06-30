package com.incubationlab.edoctors_doctors.Login.ui;



import static android.content.Context.MODE_PRIVATE;
import static com.incubationlab.edoctors_doctors.StaticData.DOCTOR_ACCESS_TOKEN;
import static com.incubationlab.edoctors_doctors.StaticData.DOCTOR_LOGIN_SHARED_PREFS;
import static com.incubationlab.edoctors_doctors.StaticData.LoggedInDoctorData;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.incubationlab.edoctors_doctors.Main.MainActivity;
import com.incubationlab.edoctors_doctors.Models.DoctorDataModel;
import com.incubationlab.edoctors_doctors.R;
import com.incubationlab.edoctors_doctors.Repository.RemoteRequestInterface;


public class LoginFragment extends Fragment implements View.OnClickListener {


    private EditText etPhone, etPassword,etBmdc;
    private TextView tvForgotPass, tvRegister;
    private Button signInButton;
    private LoginViewModel loginViewModel;
    private ConstraintLayout parentLayout;
    public LoginFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root= inflater.inflate(R.layout.fragment_login, container, false);

        signInButton= root.findViewById(R.id.sing_in);
        etPhone = root.findViewById(R.id.phone_no);
        etPassword = root.findViewById(R.id.password);
        tvForgotPass =root.findViewById(R.id.forgot_pass);
        tvRegister =root.findViewById(R.id.register_text);
        etBmdc = root.findViewById(R.id.et_login_bmdc_id);
        parentLayout = root.findViewById(R.id.login_parent);
        loginViewModel=new ViewModelProvider(this).get(LoginViewModel.class);

        signInButton.setOnClickListener(this);
        tvRegister.setOnClickListener(this);
        tvForgotPass.setOnClickListener(this);




        return root;
    }
    private Boolean checkIfEmpty(EditText[] evs) {
        for (EditText v:
             evs) {
            if(v.getText().toString().isEmpty())
            {
                v.setError("Field Required");
                v.requestFocus();
                return true;
            }
        }

        return false;
    }

    @Override
    public void onClick(View v) {
        if(v == signInButton){
            loginDoctor();
        }
        else if(v == tvRegister){
            Navigation.createNavigateOnClickListener(R.id.nav_mobile_verification).onClick(v);
        }
        else if(v == tvForgotPass){
            Navigation.createNavigateOnClickListener(R.id.nav_reset_pass).onClick(v);

        }

    }

    private void loginDoctor() {
        if(!checkIfEmpty(new EditText[] {etBmdc,etPhone,etPassword})){
            String phone = "+88" + etPhone.getText().toString();
            String password = etPassword.getText().toString();
            String doctorId = etBmdc.getText().toString();

            loginViewModel.login(new DoctorDataModel(doctorId,phone,password), new RemoteRequestInterface() {
                @Override
                public void onSuccess(int code, String msg) {
                    if(code == 202){
                        saveDataInSharedPrefs();
                        Toast.makeText(getContext(), "Welcome Dr. " + LoggedInDoctorData.getValue().getLastName(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(String msg) {
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                }
            });
        }




    }
    private void saveDataInSharedPrefs(){
        SharedPreferences loginPrefs = getContext().getSharedPreferences(DOCTOR_LOGIN_SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor loginPrefsEditor = loginPrefs.edit();
        loginPrefsEditor.putString(DOCTOR_ACCESS_TOKEN,LoggedInDoctorData.getValue().getToken());
        loginPrefsEditor.apply();
    }
}
