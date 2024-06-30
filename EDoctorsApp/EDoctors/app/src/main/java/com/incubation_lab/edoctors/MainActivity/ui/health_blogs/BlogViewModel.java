package com.incubation_lab.edoctors.MainActivity.ui.health_blogs;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.incubation_lab.edoctors.Models.BlogDataModel;
import com.incubation_lab.edoctors.Repository.BlogRepository;

import java.util.ArrayList;

public class BlogViewModel extends ViewModel {
    private BlogRepository blogRepository;

    public BlogViewModel() {
        blogRepository = new BlogRepository();
    }


    public LiveData<ArrayList<BlogDataModel>> getAllBlogs() {
        return blogRepository.getAllBlogs();
    }

}
