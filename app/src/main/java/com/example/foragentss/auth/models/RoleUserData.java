package com.example.foragentss.auth.models;

import java.util.ArrayList;
import java.util.List;

public class RoleUserData {
    private int id;
    private String role;
    private List<RoleUser> user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<RoleUser> getUser() {
        return user;
    }

    public void setUser(ArrayList<RoleUser> user) {
        this.user = user;
    }
}
