package com.incubationlab.edoctors_doctors.Main.ui.HealthBlogs.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incubationlab.edoctors_doctors.R;

public class BlogViewHolder extends RecyclerView.ViewHolder {
    ImageView ivBlog;
    TextView tvBlogTitle;
    public BlogViewHolder(@NonNull View itemView) {
        super(itemView);
        ivBlog = itemView.findViewById(R.id.blog_headlines_image);
        tvBlogTitle = itemView.findViewById(R.id.blog_headline);
    }
}
