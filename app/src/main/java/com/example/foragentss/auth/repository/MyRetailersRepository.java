package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.response.ConnectionsResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.MyRetailerInterface;

import retrofit2.Call;

public class MyRetailersRepository {
    private MyRetailerInterface myRetailerInterface;

    public MyRetailersRepository(){
        myRetailerInterface = RetrofitRequest.getApiInstance().create(MyRetailerInterface.class);
    }

    public Call<ConnectionsResponse> show(int status){
        return myRetailerInterface.show(status);
    }
}
