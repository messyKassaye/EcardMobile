package com.example.foragentss.auth.retrofit.interfaces;


import com.example.foragentss.auth.response.MeResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MeInterface {
    @GET("me")
    Call<MeResponse> me();
}
