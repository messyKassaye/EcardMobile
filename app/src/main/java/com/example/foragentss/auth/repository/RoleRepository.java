package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.response.RoleResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.RoleInterface;

import retrofit2.Call;

public class RoleRepository {
    private RoleInterface roleInterface;

    public RoleRepository(){
        roleInterface = RetrofitRequest.getApiInstance().create(RoleInterface.class);
    }

    public Call<RoleResponse> show(int id){
        return  roleInterface.show(id);
    }
}
