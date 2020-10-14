package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.Connect;
import com.example.foragentss.auth.response.ConnectionsResponse;
import com.example.foragentss.auth.response.SuccessResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ConnectionInterface {
    @POST("connect")
    Observable<SuccessResponse> connect(@Body Connect connect);

    @GET("connect/{status}")
    Call<ConnectionsResponse> show(@Path("status") String status);
}
