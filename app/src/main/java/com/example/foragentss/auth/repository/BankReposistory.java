package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.Bank;
import com.example.foragentss.auth.response.BankResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.BanksInterface;

import java.util.ArrayList;

import retrofit2.Call;

public class BankReposistory {
    private BanksInterface banksInterface;

    public BankReposistory(){
        banksInterface = RetrofitRequest.getApiInstance().create(BanksInterface.class);
    }

    public Call<BankResponse> index(){
        return banksInterface.index();
    }
}
