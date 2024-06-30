package com.incubationlab.edoctors_doctors.Main.ui.HealthBlogs.recycler;

import static com.incubationlab.edoctors_doctors.Repository.RemoteAPI.RetroInstance.BASE_URL;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incubationlab.edoctors_doctors.Models.BlogDataModel;
import com.incubationlab.edoctors_doctors.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BlogAdapter extends RecyclerView.Adapter<BlogViewHolder> {
    private ArrayList<BlogDataModel> allBlogs=new ArrayList<>();
    private BlogClickListener blogClickListener;

    public BlogAdapter(BlogClickListener blogClickListener) {
        this.blogClickListener = blogClickListener;
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blogClickListener.onclick(allBlogs.get(holder.getAdapterPosition()));
            }
        });

    }

    @Override
    public int getItemCount() {

        return allBlogs.size();
    }

    public void setAllBlogs(ArrayList<BlogDataModel> allBlogs) {

        this.allBlogs = allBlogs;
        notifyDataSetChanged();
    }
}
