package com.incubation_lab.edoctors.Doctors;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.incubation_lab.edoctors.Models.DoctorDataModel;
import com.incubation_lab.edoctors.R;
import com.incubation_lab.edoctors.Repository.Remote.RetroInstance;
import com.incubation_lab.edoctors.Repository.Remote.RetroInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.incubation_lab.edoctors.Doctors.StaticDoctorData.loggedInDoctorData;

public class DoctorLoginActivity extends AppCompatActivity {

    private EditText id,pass;
    private Button signInBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_login);

        id = findViewById(R.id.doctor_id);
        pass = findViewById(R.id.password);
        signInBtn = findViewById(R.id.sing_in_btn);

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String doctorId = id.getText().toString();
                String password = pass.getText().toString();

                loginDoctor(doctorId,password);
            }
        });

    }

    private void loginDoctor(String doctorId, String password) {

        RetroInterface retroInterface= RetroInstance.getRetro();

        Call<DoctorDataModel> call = retroInterface.loginDoctor(new DoctorDataModel(doctorId, password));
        call.enqueue(new Callback<DoctorDataModel>() {
            @Override
            public void onResponse(Call<DoctorDataModel> call, Response<DoctorDataModel> response) {
                if(response.isSuccessful()) {
                    if (response.code() == 200) {

                        loggedInDoctorData.setValue(response.body());
                        Toast.makeText(DoctorLoginActivity.this, loggedInDoctorData.getValue().getBmdc(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(DoctorLoginActivity.this, AppointmentListActivity.class));
                    }
                }
            }

            @Override
            public void onFailure(Call<DoctorDataModel> call, Throwable t) {
                Toast.makeText(DoctorLoginActivity.this, "Fatal Error", Toast.LENGTH_SHORT).show();

            }
        });
    }
}