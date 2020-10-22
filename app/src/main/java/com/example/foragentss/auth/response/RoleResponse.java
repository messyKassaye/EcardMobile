package com.example.foragentss.auth.response;

import com.example.foragentss.auth.models.RoleUser;
import com.example.foragentss.auth.models.RoleUserData;

import java.util.ArrayList;
import java.util.List;

public class RoleResponse {
    private RoleUserData data;

    public RoleUserData getData() {
        return data;
    }

    public void setData(RoleUserData data) {
        this.data = data;
    }
}
