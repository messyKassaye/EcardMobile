package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.response.NearByResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NearByInterface {

    @GET("near_by")
    Call<NearByResponse> index();
}
