package com.incubationlab.edoctors_doctors.Login.ui;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.incubationlab.edoctors_doctors.R;
import com.incubationlab.edoctors_doctors.Repository.RemoteRequestInterface;


import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;


public class MobileVerificationFragment extends Fragment implements View.OnClickListener{
    private TextView getOptBtn;
    private Button continueBtn;
    private EditText phoneEditText,otpEditText;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private String verificationID;
    private String phoneNumber;
    private ConstraintLayout parentLayout;

    LoginViewModel loginViewModel;


    public MobileVerificationFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View root =  inflater.inflate(R.layout.fragment_mobile_verification, container, false);
        getOptBtn = root.findViewById(R.id.get_otp);
        continueBtn = root.findViewById(R.id.continue_btn);
        phoneEditText = root.findViewById(R.id.phone_no);
        otpEditText = root.findViewById(R.id.otp);
        progressBar = root.findViewById(R.id.progress_bar);
        mAuth=FirebaseAuth.getInstance();
        loginViewModel=new ViewModelProvider(this).get(LoginViewModel.class);
        parentLayout = root.findViewById(R.id.mvParent);

        getOptBtn.setOnClickListener(this);
        continueBtn.setOnClickListener(this);

        phoneEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                getOptBtn.setEnabled(s.length() ==11);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });



        return root;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_otp:
                sendOtp(phoneEditText.getText().toString());
                progressBar.setVisibility(View.VISIBLE);

                break;
            case R.id.continue_btn:
                String code= otpEditText.getText().toString();
                PhoneAuthCredential credential =PhoneAuthProvider.getCredential(verificationID,code);
                signIn(credential);
                progressBar.setVisibility(View.VISIBLE);

                break;
        }
    }

    private void sendOtp(String number) {


        phoneNumber="+88"+number;

        loginViewModel.findPhone(phoneNumber, new RemoteRequestInterface() {
            @Override
            public void onSuccess(int code, String msg) {
                if(code == 0){
                    Snackbar.make(parentLayout,"Phone Number already used for another account",Snackbar.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
                else {
                    continueBtn.setEnabled(true);
                    otpEditText.setEnabled(true);
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(phoneNumber).setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(getActivity()).setCallbacks(mCallBacks)
                            .build();

                    PhoneAuthProvider.verifyPhoneNumber(options);
                }
            }

            @Override
            public void onFailure(String msg) {
                Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });





    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks =new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull @NotNull PhoneAuthCredential phoneAuthCredential) {

            String verificationCode= phoneAuthCredential.getSmsCode();

            if(verificationCode!=null)
                otpEditText.setText(phoneAuthCredential.getSmsCode());
        }

        @Override
        public void onVerificationFailed(@NonNull @NotNull FirebaseException e) {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
        }

        @Override
        public void onCodeSent(@NonNull @NotNull String s, @NonNull @NotNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationID=s;
            progressBar.setVisibility(View.GONE);


        }
    };
    private void signIn(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){

                    Bundle bundle = new Bundle();
                    bundle.putString("phone", phoneNumber);

// Set Fragmentclass Arguments
                    Navigation.findNavController(getActivity(),R.id.nav_host_fragment_login).navigate(R.id.action_nav_mobile_verification_to_nav_registration,bundle);

                }
                else {
                    Toast.makeText(getContext(), "Wrong code", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}