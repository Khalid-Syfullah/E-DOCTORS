package com.incubation_lab.edoctors.MainActivity.ui.home.recycler;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.incubation_lab.edoctors.Models.CategoryDataModel;
import com.incubation_lab.edoctors.R;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesViewHolder> {
    private ArrayList<CategoryDataModel> categoryData = new ArrayList<>();
    private CategoryClickListener categoryClickListener;

    public CategoriesAdapter(CategoryClickListener categoryClickListener) {
        this.categoryClickListener = categoryClickListener;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.category_child,parent,false);
        return new CategoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        CategoryDataModel categoryDataModel = categoryData.get(position);
        holder.catName.setText(categoryDataModel.getCatName());
        holder.catImg.setImageResource(categoryDataModel.getCatImgId());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClickListener.onclick(categoryDataModel);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryData.size();
    }

    public void setCategoryData(ArrayList<CategoryDataModel> categoryData) {
        this.categoryData = categoryData;
        notifyDataSetChanged();
    }
}
