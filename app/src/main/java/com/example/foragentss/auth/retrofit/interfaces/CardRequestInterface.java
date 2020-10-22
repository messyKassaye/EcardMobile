package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.response.CardRequestResponse;
import com.example.foragentss.auth.response.SuccessResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CardRequestInterface {

    @GET("card_request/{status}")
    Call<CardRequestResponse> show(@Path("status") String status);

    @POST("card_request")
    Observable<SuccessResponse> store(@Body CardRequest cardRequest);

    @FormUrlEncoded
    @PATCH("agents/card_request_approval/{id}")
    Observable<SuccessResponse> update(@Path("id")int id, @Field("status")String status);
}
