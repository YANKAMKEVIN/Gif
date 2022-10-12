package com.kevin.netgemtest.Model;

public class DataModel {

    private String imageUrl;



    //Contructor
    public DataModel(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
