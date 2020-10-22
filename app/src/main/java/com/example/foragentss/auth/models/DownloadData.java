package com.example.foragentss.auth.models;

import java.util.ArrayList;

public class DownloadData {
    private ArrayList<DownloadCard> data;
    private boolean status;
    private String message;
    private int left_cards;

    public ArrayList<DownloadCard> getData() {
        return data;
    }

    public void setData(ArrayList<DownloadCard> data) {
        this.data = data;
    }

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

    public int getLeft_cards() {
        return left_cards;
    }

    public void setLeft_cards(int left_cards) {
        this.left_cards = left_cards;
    }
}
