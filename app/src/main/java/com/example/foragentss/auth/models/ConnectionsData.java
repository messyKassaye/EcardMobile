package com.example.foragentss.auth.models;

import java.util.ArrayList;

public class ConnectionsData {
    private ArrayList<User> user;
    private int id;
    private int status;

    public ArrayList<User> getUser() {
        return user;
    }

    public void setUser(ArrayList<User> user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
