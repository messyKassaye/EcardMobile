package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.response.RoleResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RoleInterface {

    @GET("roles/{id}")
    Call<RoleResponse> show(@Path("id")int id);
}
