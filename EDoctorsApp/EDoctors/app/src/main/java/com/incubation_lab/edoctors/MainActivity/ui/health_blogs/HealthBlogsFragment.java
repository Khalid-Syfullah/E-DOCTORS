package com.incubation_lab.edoctors.MainActivity.ui.health_blogs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.incubation_lab.edoctors.MainActivity.ui.health_blogs.recycler.BlogAdapter;
import com.incubation_lab.edoctors.MainActivity.ui.health_blogs.recycler.BlogClickListener;
import com.incubation_lab.edoctors.Models.BlogDataModel;
import com.incubation_lab.edoctors.R;

public class HealthBlogsFragment extends Fragment {

    private RecyclerView rvBlogs;
    private BlogViewModel blogViewModel;
    private BlogAdapter blogAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_health_blogs, container, false);
        rvBlogs = root.findViewById(R.id.rv_blogs);

        blogViewModel = new ViewModelProvider(this).get(BlogViewModel.class);
        blogAdapter = new BlogAdapter(new BlogClickListener() {
            @Override
            public void onclick(BlogDataModel blogData) {
//                TODO: go to details
                Bundle bundle=new Bundle();
                bundle.putSerializable("blog_data",blogData);
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment).navigate(R.id.action_navigation_health_blogs_to_navigation_blog_detail,bundle);


            }
        },-1);
        blogViewModel.getAllBlogs().observe(getViewLifecycleOwner(),blogDataModels -> {

            blogAdapter.setAllBlogs(blogDataModels);

        });
        rvBlogs.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        rvBlogs.setAdapter(blogAdapter);


        return root;
    }
}