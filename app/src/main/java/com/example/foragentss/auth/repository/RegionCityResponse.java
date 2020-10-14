package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.RegionCity;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class RegionCityResponse {
    @SerializedName("data")
    @Expose
    private ArrayList<RegionCity> data;

    public ArrayList<RegionCity> getData() {
        return data;
    }

    public void setData(ArrayList<RegionCity> data) {
        this.data = data;
    }
}
