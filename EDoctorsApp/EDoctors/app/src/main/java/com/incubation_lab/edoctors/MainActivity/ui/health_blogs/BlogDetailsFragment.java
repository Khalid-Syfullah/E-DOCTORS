package com.incubation_lab.edoctors.MainActivity.ui.health_blogs;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import com.incubation_lab.edoctors.Models.BlogDataModel;
import com.incubation_lab.edoctors.R;
import com.squareup.picasso.Picasso;

public class BlogDetailsFragment extends Fragment {

    private TextView title, description,date;
    private ImageView blogImg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_blog_details, container, false);
        title = root.findViewById(R.id.tv_details_blog_headline);
        description = root.findViewById(R.id.blog_description);
        blogImg = root.findViewById(R.id.blog_image);
        date = root.findViewById(R.id.tv_date);
        BlogDataModel blogData = (BlogDataModel) getArguments().getSerializable("blog_data");
        title.setText(blogData.getTitle());
        description.setText(blogData.getDescription());
        date.setText(blogData.getDate());

        Picasso.get().load(blogData.getImageUrl()).into(blogImg);




        return root;
    }
}
