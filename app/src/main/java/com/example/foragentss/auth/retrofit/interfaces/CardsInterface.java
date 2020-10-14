package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.response.CardsResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CardsInterface {

    @GET("cards")
    Call<CardsResponse> index();
}
