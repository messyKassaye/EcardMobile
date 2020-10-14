package com.example.foragentss.auth.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MyCardRequestData {
    @SerializedName("data")
    @Expose
    private ArrayList<MyCardRequest> data;

    public ArrayList<MyCardRequest> getData() {
        return data;
    }

    public void setData(ArrayList<MyCardRequest> data) {
        this.data = data;
    }
}
