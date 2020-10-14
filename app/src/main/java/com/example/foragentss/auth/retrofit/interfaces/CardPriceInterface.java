package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.CardPrice;
import com.example.foragentss.auth.response.SuccessResponse;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CardPriceInterface {
    @GET("card_price")
    Call<ArrayList<CardPrice>> index();

    @POST("card_price")
    Observable<SuccessResponse> store(@Body CardPrice cardPrice);

    @PUT("card_price/{id}")
    Observable<SuccessResponse> update(@Path("id") int id,@Body CardPrice cardPrice);
}
