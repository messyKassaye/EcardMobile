package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.Device;
import com.example.foragentss.auth.response.SuccessResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DeviceInterface {

    @POST("retailers/devices")
    Observable<SuccessResponse> store(@Body Device device);

    @PUT("retailers/devices/{id}")
    Observable<SuccessResponse> update(@Path("id")int id, @Body Device device);
}
