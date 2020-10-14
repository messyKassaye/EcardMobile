package com.example.foragentss.auth.response;

import com.example.foragentss.auth.models.CardRequestData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CardRequestResponse {
    @SerializedName("data")
    @Expose
    private ArrayList<CardRequestData> data;

    public ArrayList<CardRequestData> getData() {
        return data;
    }

    public void setData(ArrayList<CardRequestData> data) {
        this.data = data;
    }
}
