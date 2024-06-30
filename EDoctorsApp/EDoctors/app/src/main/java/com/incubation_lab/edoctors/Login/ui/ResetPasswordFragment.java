package com.incubation_lab.edoctors.Login.ui;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.incubation_lab.edoctors.Login.LoginActivity;
import com.incubation_lab.edoctors.Models.UserDataModel;
import com.incubation_lab.edoctors.R;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import static com.incubation_lab.edoctors.StaticData.STATUS_PASSWORD_UPDATED;
import static com.incubation_lab.edoctors.StaticData.USER_EXISTS;
import static com.incubation_lab.edoctors.StaticData.USER_NOT_FOUND;


public class ResetPasswordFragment extends Fragment implements View.OnClickListener {
    private TextView getOptBtn,verifyBtn;
    private Button continueBtn;
    private EditText phoneEditText,otpEditText,newPassEdittext,reTypePassEditText;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private String verificationID;
    private String phoneNumber;

    private LoginViewModel loginViewModel;


    public ResetPasswordFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View root =  inflater.inflate(R.layout.fragment_reset_password, container, false);
        getOptBtn = root.findViewById(R.id.get_otp);
        continueBtn = root.findViewById(R.id.continue_btn);
        phoneEditText = root.findViewById(R.id.phone_no);
        otpEditText = root.findViewById(R.id.otp);
        newPassEdittext = root.findViewById(R.id.new_pass);
        verifyBtn = root.findViewById(R.id.verify);
        reTypePassEditText = root.findViewById(R.id.retype_password);
        progressBar = root.findViewById(R.id.progress_bar);
        mAuth=FirebaseAuth.getInstance();
        loginViewModel=new ViewModelProvider(this).get(LoginViewModel.class);

        getOptBtn.setOnClickListener(this);
        continueBtn.setOnClickListener(this);
        verifyBtn.setOnClickListener(this);

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
            case R.id.verify:
                String code= otpEditText.getText().toString();
                progressBar.setVisibility(View.VISIBLE);
                PhoneAuthCredential credential =PhoneAuthProvider.getCredential(verificationID,code);
                signIn(credential);
                break;
            case R.id.continue_btn:
                progressBar.setVisibility(View.VISIBLE);

                resetPass();

                break;
        }
    }

    private void resetPass() {
        String newpass= newPassEdittext.getText().toString();
        String reTypePass= reTypePassEditText.getText().toString();

        if(newpass.equals(reTypePass)){
            loginViewModel.updatePass(new UserDataModel(phoneNumber,newpass));
            loginViewModel.getLoginStatus().observe(this, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    if(s.equals(STATUS_PASSWORD_UPDATED)){
                        LoginFragment loginFragment = new LoginFragment();

                        ((LoginActivity) getActivity()).setFragment(loginFragment);
                    }
                }
            });

        }
        else {
            reTypePassEditText.setError("passwords does not match");
            progressBar.setVisibility(View.GONE);
        }
    }

    private void sendOtp(String number) {


        phoneNumber="+88"+number;
        loginViewModel.findPhone(phoneNumber);

        loginViewModel.getLoginStatus().observe(this,s ->  {

                if(s.equals(USER_NOT_FOUND)){
                    Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);

                }
                else if(s.equals(USER_EXISTS)){
                    continueBtn.setEnabled(true);
                    otpEditText.setEnabled(true);
                    PhoneAuthOptions options = PhoneAuthOptions.newBuilder(mAuth)
                            .setPhoneNumber(phoneNumber).setTimeout(60L, TimeUnit.SECONDS)
                            .setActivity(getActivity()).setCallbacks(mCallBacks)
                            .build();

                    PhoneAuthProvider.verifyPhoneNumber(options);
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
            phoneEditText.setEnabled(false);
            verifyBtn.setEnabled(true);


        }
    };
    private void signIn(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    verifyBtn.setEnabled(false);
                    getOptBtn.setEnabled(false);
                    phoneEditText.setEnabled(false);
                    otpEditText.setEnabled(false);

                    newPassEdittext.setEnabled(true);
                    reTypePassEditText.setEnabled(true);

                    continueBtn.setEnabled(true);

                }
                else {
                    Toast.makeText(getContext(), "Wrong code", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}