package com.example.foragentss.auth.models;

import java.io.Serializable;

public class Verification implements Serializable {
    private int user_id;
    private String is_verified;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getIs_verified() {
        return is_verified;
    }

    public void setIs_verified(String is_verified) {
        this.is_verified = is_verified;
    }
}
