package com.incubationlab.edoctors_doctors.Main.ui.HealthBlogs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonObject;
import com.incubationlab.edoctors_doctors.Models.BlogDataModel;
import com.incubationlab.edoctors_doctors.Repository.BlogRepository;

import java.util.ArrayList;

public class BlogViewModel extends ViewModel {
    private BlogRepository blogRepository;

    public BlogViewModel() {
        blogRepository = new BlogRepository();
    }
    public void uploadBlog(BlogDataModel blogDataModel){
        blogRepository.uploadBlog(blogDataModel);
    }

    public LiveData<ArrayList<BlogDataModel>> getAllBlogs() {
        return blogRepository.getAllBlogs();
    }

    public void updateBlog(BlogDataModel blogDataModel) {

        blogRepository.updateBlog(blogDataModel);

    }
}
