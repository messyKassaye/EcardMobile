package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.CardRequestPayment;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.CardRequestPaymentInterface;

import io.reactivex.Observable;

public class CardRequestPaymentRepository {
    private CardRequestPaymentInterface cardRequestPaymentInterface;

    public CardRequestPaymentRepository(){
        cardRequestPaymentInterface = RetrofitRequest.getApiInstance().create(CardRequestPaymentInterface.class);

    }

    public Observable<SuccessResponse> store(CardRequestPayment cardRequestPayment){
        return cardRequestPaymentInterface.store(cardRequestPayment);
    }
}
