package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.CardType;
import com.example.foragentss.auth.response.CardTypeResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CardTypeInterface {

    @GET("card_type")
    Call<ArrayList<CardType>> index();

}
