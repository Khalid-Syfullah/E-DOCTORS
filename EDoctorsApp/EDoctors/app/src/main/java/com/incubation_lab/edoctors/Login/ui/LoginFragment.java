package com.incubation_lab.edoctors.Login.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.incubation_lab.edoctors.Login.LoginActivity;
import com.incubation_lab.edoctors.MainActivity.MainActivity;
import com.incubation_lab.edoctors.Models.UserDataModel;
import com.incubation_lab.edoctors.R;

import static com.incubation_lab.edoctors.StaticData.BUNDLE_KEY;
import static com.incubation_lab.edoctors.StaticData.KEY_REGISTER;
import static com.incubation_lab.edoctors.StaticData.STATUS_LOGGED_IN;

public class LoginFragment extends Fragment {


    private EditText phoneEditText,passwordEditText;
    private TextView forgotPass,register;
    private Button signInButton;
    private LoginViewModel loginViewModel;
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
        phoneEditText = root.findViewById(R.id.phone_no);
        passwordEditText= root.findViewById(R.id.password);
        forgotPass=root.findViewById(R.id.forgot_pass);
        register=root.findViewById(R.id.register_text);
        loginViewModel=new ViewModelProvider(this).get(LoginViewModel.class);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!checkIfEmpty(phoneEditText)&&!checkIfEmpty(passwordEditText)){
                   loginViewModel.login(new UserDataModel("+88"+phoneEditText.getText().toString(),passwordEditText.getText().toString()));
               }



            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString(BUNDLE_KEY, KEY_REGISTER);
                MobileVerificationFragment mobileVerificationFragment=new MobileVerificationFragment();
                mobileVerificationFragment.setArguments(bundle);
                ((LoginActivity) getActivity()).setFragment(mobileVerificationFragment);
            }
        });
        loginViewModel.getLoginStatus().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if(s.equals(STATUS_LOGGED_IN)){

                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity().finish();
                }

            }
        });

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


// Set Fragmentclass Arguments
                ResetPasswordFragment rpf = new ResetPasswordFragment();

                ((LoginActivity) getActivity()).setFragment(rpf);
            }
        });

        return root;
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
