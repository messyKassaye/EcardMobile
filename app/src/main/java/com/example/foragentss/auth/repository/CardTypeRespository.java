package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.CardType;
import com.example.foragentss.auth.response.CardTypeResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.CardTypeInterface;

import java.util.ArrayList;

import retrofit2.Call;

public class CardTypeRespository {
    private CardTypeInterface cardTypeInterface;

    public CardTypeRespository() {
        cardTypeInterface = RetrofitRequest.getApiInstance().create(CardTypeInterface.class);
    }

    public Call<ArrayList<CardType>> index(){
        return  cardTypeInterface.index();
    }
}
