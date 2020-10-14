package com.example.foragentss.auth.models;

import java.util.ArrayList;

public class ConnectionsData {
    private int id;
    private ArrayList<User> user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<User> getUser() {
        return user;
    }

    public void setUser(ArrayList<User> user) {
        this.user = user;
    }
}
