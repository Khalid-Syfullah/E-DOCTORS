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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.incubationlab.edoctors_doctors.R;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;


public class ResetPasswordFragment extends Fragment  {
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






        return null;

    }


}