package com.incubation_lab.edoctors.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.incubation_lab.edoctors.Models.BlogDataModel;
import com.incubation_lab.edoctors.Models.ResponseModel;
import com.incubation_lab.edoctors.Repository.Remote.RetroInstance;
import com.incubation_lab.edoctors.Repository.Remote.RetroInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BlogRepository {

    private MutableLiveData<ArrayList<BlogDataModel>> allBlogs;
    private RetroInterface retroInterface;
    public BlogRepository() {
        retroInterface = RetroInstance.getRetro();
        allBlogs = new MutableLiveData<>();

    }




    public LiveData<ArrayList<BlogDataModel>> getAllBlogs() {
        retroInterface.getBlogs().enqueue(new Callback<ArrayList<BlogDataModel>>() {
            @Override
            public void onResponse(Call<ArrayList<BlogDataModel>> call, Response<ArrayList<BlogDataModel>> response) {
                if(response.isSuccessful()&& response.code()==200){
                    allBlogs.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<BlogDataModel>> call, Throwable t) {

            }
        });
        return allBlogs;
    }


}
