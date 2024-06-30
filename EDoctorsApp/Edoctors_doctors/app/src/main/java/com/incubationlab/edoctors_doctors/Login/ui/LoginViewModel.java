package com.incubationlab.edoctors_doctors.Login.ui;

import androidx.lifecycle.ViewModel;

import com.incubationlab.edoctors_doctors.Models.DoctorDataModel;
import com.incubationlab.edoctors_doctors.Repository.LoginRepository;
import com.incubationlab.edoctors_doctors.Repository.RemoteRequestInterface;

public class LoginViewModel extends ViewModel {
    private LoginRepository loginRepository;
    public LoginViewModel(){
        loginRepository = new LoginRepository();
    }

    public void findPhone(String phoneNumber, RemoteRequestInterface requestInterface) {
        loginRepository.findDoctorByPhone(phoneNumber,requestInterface);

    }
    public void register(DoctorDataModel doctorDataModel,RemoteRequestInterface requestInterface){
        loginRepository.register(doctorDataModel,requestInterface);
    }

    public void login(DoctorDataModel doctorDataModel, RemoteRequestInterface requestInterface) {
        loginRepository.login(doctorDataModel,requestInterface);
    }

    public void validateToken(String token,RemoteRequestInterface requestInterface) {
        loginRepository.validateToken(token,requestInterface);
    }
}
