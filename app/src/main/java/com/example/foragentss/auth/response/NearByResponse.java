package com.example.foragentss.auth.response;

import com.example.foragentss.auth.models.NearbyData;

import java.util.ArrayList;

public class NearByResponse {
    private boolean status;
    private String message;
    private ArrayList<NearbyData> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<NearbyData> getData() {
        return data;
    }

    public void setData(ArrayList<NearbyData> data) {
        this.data = data;
    }
}
