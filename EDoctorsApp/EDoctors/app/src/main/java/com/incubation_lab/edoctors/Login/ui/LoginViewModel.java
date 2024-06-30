package com.incubation_lab.edoctors.Login.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.incubation_lab.edoctors.Models.UserDataModel;
import com.incubation_lab.edoctors.Models.UserImageModel;
import com.incubation_lab.edoctors.Repository.Remote.RetroInstance;
import com.incubation_lab.edoctors.Repository.Remote.RetroInterface;
import com.incubation_lab.edoctors.Repository.UserRepository;

import org.jetbrains.annotations.NotNull;

public class LoginViewModel extends AndroidViewModel {
    private RetroInterface retroInterface;
    private UserRepository repository;
    

    public LoginViewModel(@NonNull @NotNull Application application) {
        super(application);
        retroInterface= RetroInstance.getRetro();
        repository=new UserRepository(application);

    }
    public void register(UserDataModel userDataModel){
        repository.register(userDataModel);
    }

    public void login(UserDataModel userDataModel){
        repository.login(userDataModel);
    }
    public void validateToken(String token){
        repository.validateToken(token);
    }
    public void findPhone(String phone){
        repository.findPhone(phone);
    }

    public LiveData<String> getLoginStatus() {
        return repository.getLoginStatus();
    }
    public LiveData<UserDataModel> getLoggedInUser(){return  repository.getLoggedInUser();}

    public void updatePass(UserDataModel userDataModel) {
        repository.updatePass(userDataModel);
    }

    public void updatePicture(UserImageModel encodedImage) {
        repository.updatePicture(encodedImage);
    }
}
