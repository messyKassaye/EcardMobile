package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.response.ConnectionsResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.MyPartnersInterface;

import java.util.ArrayList;

import retrofit2.Call;

public class MyPartnersRepository {

    private MyPartnersInterface myPartnersInterface;

    public MyPartnersRepository(){
        myPartnersInterface = RetrofitRequest.getApiInstance().create(MyPartnersInterface.class);
    }

    public Call<ConnectionsResponse> show(int status){
        return  myPartnersInterface.show(status);
    }
}
