package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.repository.RegionCityResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RegionCityInterface {
    @GET("region_city")
    Call<RegionCityResponse> index();
}
