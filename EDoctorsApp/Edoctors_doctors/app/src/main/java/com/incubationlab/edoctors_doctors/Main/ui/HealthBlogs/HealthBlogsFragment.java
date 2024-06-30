package com.incubationlab.edoctors_doctors.Main.ui.HealthBlogs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.incubationlab.edoctors_doctors.Main.ui.HealthBlogs.recycler.BlogAdapter;
import com.incubationlab.edoctors_doctors.Main.ui.HealthBlogs.recycler.BlogClickListener;
import com.incubationlab.edoctors_doctors.Models.BlogDataModel;
import com.incubationlab.edoctors_doctors.R;


public class HealthBlogsFragment extends Fragment {
    private RecyclerView rvBlogs;
    private FloatingActionButton fab;
    private BlogViewModel blogViewModel;
    private BlogAdapter blogAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_health_blogs, container, false);
        fab = root.findViewById(R.id.blogFab);
        rvBlogs = root.findViewById(R.id.rv_blogs);

        blogViewModel = new ViewModelProvider(this).get(BlogViewModel.class);
        blogAdapter = new BlogAdapter(new BlogClickListener() {
            @Override
            public void onclick(BlogDataModel blogData) {
//                TODO: go to details
                Bundle bundle=new Bundle();
                bundle.putSerializable("blog_data",blogData);
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_main).navigate(R.id.action_navigation_health_blogs_to_navigation_blog_details,bundle);


            }
        });
        blogViewModel.getAllBlogs().observe(getViewLifecycleOwner(),blogDataModels -> {

            blogAdapter.setAllBlogs(blogDataModels);

        });
        rvBlogs.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
        rvBlogs.setAdapter(blogAdapter);




        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(),R.id.nav_host_fragment_main).navigate(R.id.action_navigation_health_blogs_to_navigation_add_blog);
            }
        });



        return root;
    }
}