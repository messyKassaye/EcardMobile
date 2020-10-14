package com.example.foragentss.auth.response;

import com.example.foragentss.auth.models.Card;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CardsResponse {
    @SerializedName("data")
    @Expose
    private ArrayList<Card> data;

    public ArrayList<Card> getData() {
        return data;
    }

    public void setData(ArrayList<Card> data) {
        this.data = data;
    }
}
