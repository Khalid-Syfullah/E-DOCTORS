package com.incubation_lab.edoctors.Login.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.incubation_lab.edoctors.Login.LoginActivity;
import com.incubation_lab.edoctors.Models.UserDataModel;
import com.incubation_lab.edoctors.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.incubation_lab.edoctors.StaticData.STATUS_REGISTERED;


public class RegisterFragment extends Fragment implements View.OnClickListener {


    private Button signUpBtn;

    private EditText firstName;
    private EditText lastName;
    private Spinner gender,spAge;
    private EditText password;
    private EditText re_password;
    private EditText email;


    private String phone;

    private LoginViewModel loginViewModel;


    public RegisterFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_register, container, false);

        phone = getArguments().getString("number");


        firstName=root.findViewById(R.id.first_name);
        lastName=root.findViewById(R.id.last_name);
        spAge =root.findViewById(R.id.ageSpinner);
        gender=root.findViewById(R.id.gender);
        password=root.findViewById(R.id.password);
        re_password=root.findViewById(R.id.retype_password);
        email=root.findViewById(R.id.email);
        signUpBtn =root.findViewById(R.id.sign_up);

        signUpBtn.setOnClickListener(this);

        loginViewModel=new ViewModelProvider(this).get(LoginViewModel.class);

        List<Integer> age = new ArrayList<Integer>();
        for (int i = 1; i <= 100; i++) {
            age.add(i);
        }
        ArrayAdapter<Integer> spinnerArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, age);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spAge = root.findViewById(R.id.ageSpinner);
        spAge.setAdapter(spinnerArrayAdapter);



        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sign_up:
                validateData();
        }
    }

    private void validateData() {

        if(!checkIfEmpty(firstName)||!checkIfEmpty(lastName)||!checkIfEmpty(email)||!checkIfEmpty(password)||!checkIfEmpty(re_password)){
            if(!password.getText().toString().equals(re_password.getText().toString())){
                password.setError("Passwords Don't match");
                password.requestFocus();
                return;
            }
            String first_name=firstName.getText().toString();
            String last_name=lastName.getText().toString();
            String emailAddress=email.getText().toString();
            String passwordString=password.getText().toString();
            String genderString=gender.getSelectedItem().toString();
            String age = spAge.getSelectedItem().toString();

            loginViewModel.register(new UserDataModel(first_name,last_name,phone,emailAddress, age,genderString,passwordString));

            loginViewModel.getLoginStatus().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if(s.equals(STATUS_REGISTERED)){
                        Toast.makeText(getContext(), "Registration Successful", Toast.LENGTH_SHORT).show();
                        ((LoginActivity) getActivity()).setFragment(new LoginFragment());
                    }
                }
            });

        }


    }

    private Boolean checkIfEmpty(EditText v) {
        if(v.getText().toString().isEmpty())
        {
            v.setError("Field Required");
            v.requestFocus();
            return true;
        }
        return false;
    }


}