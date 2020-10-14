package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.Address;
import com.example.foragentss.auth.response.SuccessResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AddressInterface {
    @POST("address")
    Observable<SuccessResponse> store(@Body Address address);
}
