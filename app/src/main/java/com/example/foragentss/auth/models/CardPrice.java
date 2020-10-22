package com.example.foragentss.auth.models;

import java.io.Serializable;

public class CardPrice implements Serializable {
    private int id;
    private double percentage;
    private double percentage_value;
    private int user_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double getPercentage_value() {
        return percentage_value;
    }

    public void setPercentage_value(double percentage_value) {
        this.percentage_value = percentage_value;
    }
}
