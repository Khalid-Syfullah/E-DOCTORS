package com.incubation_lab.edoctors.MainActivity.ui.health_blogs.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.incubation_lab.edoctors.Models.BlogDataModel;
import com.incubation_lab.edoctors.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BlogAdapter extends RecyclerView.Adapter<BlogViewHolder> {
    private ArrayList<BlogDataModel> allBlogs=new ArrayList<>();
    private BlogClickListener blogClickListener;
    private int limit;

    public BlogAdapter(BlogClickListener blogClickListener,int limit) {
        this.blogClickListener = blogClickListener;
        this.limit = limit;
    }

    @NonNull
    @Override
    public BlogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.blog_child,parent,false);
        return new BlogViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BlogViewHolder holder, int position) {

        Picasso.get().load(allBlogs.get(position).getImageUrl()).into(holder.ivBlog);
        holder.tvBlogTitle.setText(allBlogs.get(position).getTitle());
        holder.divider.setVisibility(limit==-1?View.VISIBLE:View.GONE);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blogClickListener.onclick(allBlogs.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {

        if(limit!= -1){
            return Math.min(limit,allBlogs.size());
        }
        return allBlogs.size();
    }

    public void setAllBlogs(ArrayList<BlogDataModel> allBlogs) {

        this.allBlogs = allBlogs;
        notifyDataSetChanged();
    }
}
