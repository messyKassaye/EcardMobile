package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.response.CardsResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.CardsInterface;

import java.util.ArrayList;

import retrofit2.Call;

public class CardsRepository {
    private CardsInterface cardsInterface;

    public CardsRepository(){
        cardsInterface = RetrofitRequest.getApiInstance().create(CardsInterface.class);
    }

    public Call<CardsResponse> index(){
        return cardsInterface.index();
    }
}
