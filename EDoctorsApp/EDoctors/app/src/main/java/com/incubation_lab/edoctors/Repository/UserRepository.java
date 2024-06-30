package com.incubation_lab.edoctors.Repository;

import android.app.Application;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.incubation_lab.edoctors.Models.UserDataModel;
import com.incubation_lab.edoctors.Models.UserImageModel;
import com.incubation_lab.edoctors.Repository.Remote.RetroInstance;
import com.incubation_lab.edoctors.Repository.Remote.RetroInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.incubation_lab.edoctors.StaticData.PICTURE_UPDATE_SUCCESS;
import static com.incubation_lab.edoctors.StaticData.RESPONSE_SUCCESS;
import static com.incubation_lab.edoctors.StaticData.STATUS_LOGGED_IN;
import static com.incubation_lab.edoctors.StaticData.STATUS_REGISTERED;
import static com.incubation_lab.edoctors.StaticData.LoggedInUserData;

public class UserRepository {
    public static String LOGIN_SHARED_PREFS="user_token";
    public static String ACCESS_TOKEN="access_token";
    private MutableLiveData<String> loginStatus;
    private RetroInterface retroInterface;
    private Application application;


    public UserRepository(Application application) {
        retroInterface= RetroInstance.getRetro();
        loginStatus=new MutableLiveData<>();

        this.application= application;


    }

    public void register(UserDataModel userDataModel){
        Call<UserDataModel> call = retroInterface.register(userDataModel);
        call.enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                if(response.isSuccessful()){
                    if (response.body().getServerMsg().equals(RESPONSE_SUCCESS)){
                        Toast.makeText(application, "Registration Successful", Toast.LENGTH_SHORT).show();
                        loginStatus.setValue(STATUS_REGISTERED);
                    }
                    else{
                        Toast.makeText(application, response.body().getServerMsg(), Toast.LENGTH_SHORT).show();
                        loginStatus.setValue(response.body().getServerMsg());

                    }
                    loginStatus.setValue("");
                }
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {
                loginStatus.setValue("failed");
                Toast.makeText(application, "Network error", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void login(UserDataModel userDataModel){
        SharedPreferences loginPrefs = application.getSharedPreferences(LOGIN_SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor loginPrefsEditor = loginPrefs.edit();
        Call<UserDataModel> call = retroInterface.login(userDataModel);
        call.enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                if(response.isSuccessful()){
                    if (response.body().getServerMsg().equals(RESPONSE_SUCCESS)){

                        loginPrefsEditor.putString(ACCESS_TOKEN,response.body().getToken());
                        loginPrefsEditor.apply();
                        LoggedInUserData.setValue(response.body());
                        Toast.makeText(application, response.body().getFirstName(), Toast.LENGTH_SHORT).show();
                        loginStatus.setValue(STATUS_LOGGED_IN);
                        loginStatus.setValue("");
                    }
                    else{
                        Toast.makeText(application, response.body().getServerMsg(), Toast.LENGTH_SHORT).show();
                        loginStatus.setValue(response.body().getServerMsg());
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {
                loginStatus.setValue("failed");
                loginStatus.setValue("");
                Toast.makeText(application, "Network error", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public LiveData<String> getLoginStatus() {
        return loginStatus;
    }
    public LiveData<UserDataModel> getLoggedInUser() {
        return LoggedInUserData;
    }

    public void validateToken(String token) {
        Call<UserDataModel> call=retroInterface.validateToken(new UserDataModel(token));
        call.enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                if (response.isSuccessful()) {

                    if(response.body().getServerMsg().equals(RESPONSE_SUCCESS)) {
                        LoggedInUserData.setValue(response.body());
                        loginStatus.setValue(STATUS_LOGGED_IN);
                        loginStatus.setValue("");
                        Toast.makeText(application,"welcome "+ response.body().getFirstName(), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        loginStatus.setValue(response.body().getServerMsg());
                        loginStatus.setValue("");
                    }
                }
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {
                Toast.makeText(application, "Network Error", Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void findPhone(String phone) {
        Call<UserDataModel> call=retroInterface.findByPhone(new UserDataModel(""+phone,""));
        call.enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                if (response.isSuccessful()) {

                    loginStatus.setValue(response.body().getServerMsg());
                    loginStatus.setValue("");
                }
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {
                Toast.makeText(application, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void updatePass(UserDataModel userDataModel) {
        Call<UserDataModel> call = retroInterface.updatePassword(userDataModel);
        call.enqueue(new Callback<UserDataModel>() {
            @Override
            public void onResponse(Call<UserDataModel> call, Response<UserDataModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(application, response.body().getServerMsg(), Toast.LENGTH_SHORT).show();

                    loginStatus.setValue(response.body().getServerMsg());
                    loginStatus.setValue("");

                }
                else {
                    Toast.makeText(application, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDataModel> call, Throwable t) {
                Toast.makeText(application, "Network Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void updatePicture(UserImageModel encodedImage) {
        Call<UserImageModel> call = retroInterface.updatePicture(encodedImage);
        call.enqueue(new Callback<UserImageModel>() {
            @Override
            public void onResponse(Call<UserImageModel> call, Response<UserImageModel> response) {
                if (response.isSuccessful()) {
                    if(response.body().getServerMsg().equals(PICTURE_UPDATE_SUCCESS)){
                        loginStatus.setValue(PICTURE_UPDATE_SUCCESS);
                    }
                    Toast.makeText(application, response.body().getServerMsg(), Toast.LENGTH_SHORT).show();


                }
            }

            @Override
            public void onFailure(Call<UserImageModel> call, Throwable t) {
                Toast.makeText(application, "failed to update! try again later", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
