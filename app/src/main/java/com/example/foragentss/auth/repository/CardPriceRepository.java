package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.CardPrice;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.CardPriceInterface;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;

public class CardPriceRepository {
    private CardPriceInterface cardPriceInterface;

    public CardPriceRepository(){
        cardPriceInterface = RetrofitRequest.getApiInstance()
                .create(CardPriceInterface.class);
    }

    public Call<ArrayList<CardPrice>> index(){
        return cardPriceInterface.index();
    }

    public Observable<SuccessResponse> store(CardPrice cardPrice){
        return cardPriceInterface.store(cardPrice);
    }

    public Observable<SuccessResponse> update(int id,CardPrice cardPrice){
        return cardPriceInterface.update(id,cardPrice);
    }
}
