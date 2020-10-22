package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.CardSenderInterface;

import io.reactivex.Observable;

public class CardSendRepository {
    private CardSenderInterface cardSenderInterface;

    public CardSendRepository(){
        cardSenderInterface = RetrofitRequest.getApiInstance().create(CardSenderInterface.class);
    }

    public Observable<SuccessResponse> store(CardRequest cardRequest){
        return  cardSenderInterface.store(cardRequest);
    }
}
