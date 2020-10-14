package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.CardRequestPayment;
import com.example.foragentss.auth.response.SuccessResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CardRequestPaymentInterface {
    @POST("card_request_payment")
    Observable<SuccessResponse> store(@Body CardRequestPayment cardRequestPayment);
}
