package com.example.foragentss.auth.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Bank implements Serializable {
    private int id;
    private String name;
    private int total_user;
    private ArrayList<BankAccount> me;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal_user() {
        return total_user;
    }

    public void setTotal_user(int total_user) {
        this.total_user = total_user;
    }

    public ArrayList<BankAccount> getMe() {
        return me;
    }

    public void setMe(ArrayList<BankAccount> me) {
        this.me = me;
    }
}
