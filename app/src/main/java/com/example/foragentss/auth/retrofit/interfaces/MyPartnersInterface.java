package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.MyPartner;
import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.response.ConnectionsResponse;
import com.example.foragentss.auth.response.SuccessResponse;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MyPartnersInterface {
    @POST("partner_agent")
    Observable<SuccessResponse> store(@Body MyPartner myPartner);

    @GET("partner_agent/{status}")
    Call<ConnectionsResponse> show(@Path("status")int status);

}
