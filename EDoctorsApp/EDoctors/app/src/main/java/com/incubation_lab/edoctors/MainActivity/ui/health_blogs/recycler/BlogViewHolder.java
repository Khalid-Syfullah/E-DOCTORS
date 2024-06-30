package com.incubation_lab.edoctors.MainActivity.ui.health_blogs.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incubation_lab.edoctors.R;

public class BlogViewHolder extends RecyclerView.ViewHolder {
    ImageView ivBlog;
    TextView tvBlogTitle;
    View divider;
    public BlogViewHolder(@NonNull View itemView) {
        super(itemView);
        ivBlog = itemView.findViewById(R.id.blog_headlines_image);
        tvBlogTitle = itemView.findViewById(R.id.blog_headline);
        divider= itemView.findViewById(R.id.blog_child_divider);
    }
}
