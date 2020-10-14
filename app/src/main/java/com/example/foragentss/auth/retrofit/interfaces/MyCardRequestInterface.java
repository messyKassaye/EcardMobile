package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.MyCardRequestData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MyCardRequestInterface {
    @GET("my_card_request")
    Call<MyCardRequestData> index();
}
