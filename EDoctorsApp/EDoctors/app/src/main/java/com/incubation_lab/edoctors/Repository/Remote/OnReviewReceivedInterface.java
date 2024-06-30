package com.incubation_lab.edoctors.Repository.Remote;

import com.incubation_lab.edoctors.Models.ReviewDataModel;

import java.util.ArrayList;

public interface OnReviewReceivedInterface {
    void onReceived(ArrayList<ReviewDataModel> reviewData);
    void onFailed();
}
