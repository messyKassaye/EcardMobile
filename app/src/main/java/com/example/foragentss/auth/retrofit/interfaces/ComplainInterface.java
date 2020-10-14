package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.Complain;
import com.example.foragentss.auth.response.SuccessResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ComplainInterface {

    @POST("card_request_complain")
    Observable<SuccessResponse> store(@Body Complain complain);
}
