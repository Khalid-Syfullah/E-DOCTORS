package com.incubation_lab.edoctors.MainActivity.ui.doctors.recycler;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.incubation_lab.edoctors.Models.ReviewDataModel;
import com.incubation_lab.edoctors.R;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewViewHolder> {

    ArrayList<ReviewDataModel> allReviews;
    public ReviewsAdapter() {
        allReviews = new ArrayList<>();
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_child,parent,false);

        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {

        holder.tvReview.setText(allReviews.get(position).getReview());
        holder.ratingBar.setRating(Float.parseFloat(allReviews.get(position).getRating()));
        holder.tvDate.setText(allReviews.get(position).getDate());
        Log.d("daterev",  ""+allReviews.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return allReviews.size();
    }

    public void setAllReviews(ArrayList<ReviewDataModel> allReviews) {
        this.allReviews = allReviews;
        notifyDataSetChanged();
    }
}

class ReviewViewHolder extends RecyclerView.ViewHolder{

    TextView tvReview,tvDate;
    RatingBar ratingBar;


    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);
        tvReview =itemView.findViewById(R.id.tv_review);
        tvDate = itemView.findViewById(R.id.tv_rating_date);
        ratingBar=  itemView.findViewById(R.id.rb_rating);

    }

}
