package com.incubation_lab.edoctors.Models;

public class CategoryDataModel {

    private String catName;
    private int catImgId;

    public CategoryDataModel(String catName, int catImgId) {
        this.catName = catName;
        this.catImgId = catImgId;
    }

    public String getCatName() {
        return catName;
    }

    public int getCatImgId() {
        return catImgId;
    }
}
