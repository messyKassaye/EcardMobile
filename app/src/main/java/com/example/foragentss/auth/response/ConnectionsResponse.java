package com.example.foragentss.auth.response;

import com.example.foragentss.auth.models.ConnectionsData;

import java.util.ArrayList;

public class ConnectionsResponse {
    private ArrayList<ConnectionsData> data;

    public ArrayList<ConnectionsData> getData() {
        return data;
    }

    public void setData(ArrayList<ConnectionsData> data) {
        this.data = data;
    }
}
