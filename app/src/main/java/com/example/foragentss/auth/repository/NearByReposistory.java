package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.response.NearByResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.NearByInterface;

import retrofit2.Call;

public class NearByReposistory {
    private NearByInterface nearByInterface;

    public NearByReposistory(){
        nearByInterface = RetrofitRequest.getApiInstance().create(NearByInterface.class);

    }

    public Call<NearByResponse> index(){
        return nearByInterface.index();
    }
}
