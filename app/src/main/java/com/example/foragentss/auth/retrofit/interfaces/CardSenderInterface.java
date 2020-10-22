package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.response.SuccessResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CardSenderInterface {

    @POST("agents/card_send")
    Observable<SuccessResponse> store(@Body CardRequest cardRequest);
}
