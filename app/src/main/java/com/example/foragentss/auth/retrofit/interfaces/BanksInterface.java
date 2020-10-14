package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.Bank;
import com.example.foragentss.auth.response.BankResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BanksInterface {

    @GET("banks")
    Call<BankResponse> index();
}
