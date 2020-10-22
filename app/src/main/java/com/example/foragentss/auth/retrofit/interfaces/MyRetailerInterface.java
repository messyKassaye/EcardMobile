package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.MyPartner;
import com.example.foragentss.auth.response.ConnectionsResponse;
import com.example.foragentss.auth.response.SuccessResponse;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyRetailerInterface {

    @GET("agent_retailer/{status}")
    Call<ConnectionsResponse> show(@Path("status")int status);

    @POST("agent_retailer")
    Observable<SuccessResponse> store(@Body MyPartner myPartner);
}
