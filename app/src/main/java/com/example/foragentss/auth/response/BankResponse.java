package com.example.foragentss.auth.response;

import com.example.foragentss.auth.models.Bank;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BankResponse {
    @SerializedName("data")
    @Expose
    private ArrayList<Bank> data;

    public ArrayList<Bank> getData() {
        return data;
    }

    public void setData(ArrayList<Bank> data) {
        this.data = data;
    }
}
