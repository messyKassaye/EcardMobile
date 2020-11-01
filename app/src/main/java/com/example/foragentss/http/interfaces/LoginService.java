package com.example.foragentss.http.interfaces;



import com.example.foragentss.auth.models.LoginResponse;
import com.example.foragentss.auth.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {

    @FormUrlEncoded
    @POST("login")
    Call<LoginResponse> login(@Field("phone") String email, @Field("password") String password);

    @POST("signup")
    Call<LoginResponse> signUP(@Body User signUp);
}
