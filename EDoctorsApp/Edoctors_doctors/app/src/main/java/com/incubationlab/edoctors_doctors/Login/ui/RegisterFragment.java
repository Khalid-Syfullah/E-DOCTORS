package com.incubationlab.edoctors_doctors.Login.ui;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;


import com.google.android.material.snackbar.Snackbar;
import com.incubationlab.edoctors_doctors.Models.DoctorDataModel;
import com.incubationlab.edoctors_doctors.R;
import com.incubationlab.edoctors_doctors.Repository.RemoteRequestInterface;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class RegisterFragment extends Fragment implements View.OnClickListener {


    private TextView dateOfBirth,startingTime;
    private ConstraintLayout parentLayout;
    private Button signUpBtn;

    private EditText firstName;
    private EditText lastName;
    private Spinner gender;
    private EditText password;
    private EditText re_password;

    private EditText etQualifications;
    private EditText etDesignation;
    private EditText etBmdc;
    private EditText etBio;
    private EditText etFee;
    private EditText etSpeciality;


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

        phone = getArguments().getString("phone");


        parentLayout=root.findViewById(R.id.parentLayout);
        firstName=root.findViewById(R.id.first_name);
        lastName=root.findViewById(R.id.last_name);
        dateOfBirth=root.findViewById(R.id.date_of_birth);
        gender=root.findViewById(R.id.gender);
        password=root.findViewById(R.id.password);
        re_password=root.findViewById(R.id.retype_password);

        signUpBtn =root.findViewById(R.id.sign_up);
        startingTime = root.findViewById(R.id.starting_time);
        etDesignation = root.findViewById(R.id.designation);
        etQualifications = root.findViewById(R.id.qualifications);
        etBmdc = root.findViewById(R.id.bmdc_id);
        etBio = root.findViewById(R.id.et_bio);
        etFee = root.findViewById(R.id.et_fee);
        etSpeciality = root.findViewById(R.id.et_speciality);

        dateOfBirth.setOnClickListener(this);
        signUpBtn.setOnClickListener(this);
        startingTime.setOnClickListener(this);

        loginViewModel=new ViewModelProvider(this).get(LoginViewModel.class);




        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_of_birth:
                chooseDate();
                break;
            case R.id.sign_up:
                validateData();
                break;
            case R.id.starting_time:
                chooseTime();
                break;
        }
    }

    private void validateData() {
        Date doB;
        try {
            doB=new SimpleDateFormat("dd MMM,yyyy").parse(dateOfBirth.getText().toString());
        } catch (ParseException e) {
            dateOfBirth.setError("select date");
            dateOfBirth.requestFocus();
            return;
        }

        if(!checkIfEmpty(new EditText[]{firstName, lastName, password, re_password,etBmdc,etDesignation,etQualifications,etBio,etFee,etSpeciality})){
            if(!password.getText().toString().equals(re_password.getText().toString())){
                password.setError("Passwords Don't match");
                password.requestFocus();
                return;
            }
            String first_name=firstName.getText().toString();
            String last_name=lastName.getText().toString();
            String doctor_id = etBmdc.getText().toString();
            String passwordString=password.getText().toString();
            String genderString=gender.getSelectedItem().toString();
            String qualifications = etQualifications.getText().toString();
            String designation = etDesignation.getText().toString();
            String startTime = startingTime.getText().toString();
            String bio = etBio.getText().toString();
            String fee = etFee.getText().toString();
            String speciality = etSpeciality.getText().toString();
            loginViewModel.register(new DoctorDataModel(first_name, last_name, designation, qualifications, startTime, genderString, doctor_id, phone,fee,bio,speciality, passwordString), new RemoteRequestInterface() {
                @Override
                public void onSuccess(int code, String msg) {
                    if(code ==1){
                        Snackbar.make(parentLayout,"Registration Successful",Snackbar.LENGTH_SHORT).show();
                        Navigation.findNavController(getActivity(),R.id.nav_host_fragment_login).navigate(R.id.action_nav_registration_to_nav_login);
                    }
                    else if( code == -1 ){
                        Snackbar.make(parentLayout,"BMDC ID already exists account",Snackbar.LENGTH_SHORT).show();

                    }
                    else {
                        Snackbar.make(parentLayout,"Something went Wrong! Try again later",Snackbar.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onFailure(String msg) {
                    Snackbar.make(parentLayout,"Check your connection and try again",Snackbar.LENGTH_SHORT).show();

                }
            });
        }


    }

    private Boolean checkIfEmpty(EditText[] editTexts) {
        for (EditText v :
                editTexts) {
            if(v.getText().toString().isEmpty())
            {
                v.setError("Field Required");
                v.requestFocus();
                return true;
            }

        }
        return false;
    }

    private void chooseDate() {
        final Calendar myCalendar = Calendar.getInstance();


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "dd MMM,yyyy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dateOfBirth.setText(sdf.format(myCalendar.getTime()));
            }

        };
        new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();



    }
    private void chooseTime(){
        TimePickerDialog tpd = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(view.getHour()>=12){
                    startingTime.setText(view.getHour()-(view.getHour()==12?0:12)+" : "+view.getMinute()+ " PM");
                }
                else {
                    startingTime.setText(view.getHour()+ (view.getHour()==0?12:0) +" : "+view.getMinute()+ " AM");
                }

            }
        },Calendar.HOUR,Calendar.MINUTE,false);
        tpd.show();
    }
}