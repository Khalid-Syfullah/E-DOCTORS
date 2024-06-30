package com.incubation_lab.edoctors.MainActivity.ui.home.recycler;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incubation_lab.edoctors.R;

public class CategoriesViewHolder extends RecyclerView.ViewHolder {
    ImageView catImg;
    TextView catName;
    public CategoriesViewHolder(@NonNull View itemView) {
        super(itemView);
        catName = itemView.findViewById(R.id.tv_catName);
        catImg = itemView.findViewById(R.id.iv_catImg);
    }
}
