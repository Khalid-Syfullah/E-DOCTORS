package com.incubationlab.edoctors_doctors.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.incubationlab.edoctors_doctors.Models.BlogDataModel;
import com.incubationlab.edoctors_doctors.Models.ResponseModel;
import com.incubationlab.edoctors_doctors.Repository.RemoteAPI.RetroInstance;
import com.incubationlab.edoctors_doctors.Repository.RemoteAPI.RetroInterface;

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

    public void uploadBlog(BlogDataModel blogDataModel){
        Call<ResponseModel> call = retroInterface.uploadBlog(blogDataModel);

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });

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

    public void updateBlog(BlogDataModel blogDataModel) {
        retroInterface.updateBlog(blogDataModel).enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {

            }
        });
    }
}
