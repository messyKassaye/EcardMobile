package com.example.foragentss.auth.response;

import com.example.foragentss.auth.models.MyCardRequestData;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.MyCardRequestInterface;

import retrofit2.Call;

public class MyCardRequestRepository {
    private MyCardRequestInterface myCardRequestInterface;

    public MyCardRequestRepository(){
        myCardRequestInterface = RetrofitRequest.getApiInstance()
                .create(MyCardRequestInterface.class);
    }

    public Call<MyCardRequestData> index(){
        return myCardRequestInterface.index();
    }
}
