package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.Complain;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.ComplainInterface;

import io.reactivex.Observable;

public class ComplainRepository {
    private ComplainInterface complainInterface;

    public ComplainRepository(){
        complainInterface = RetrofitRequest.getApiInstance().create(ComplainInterface.class);
    }

    public Observable<SuccessResponse> store(Complain complain){
        return complainInterface.store(complain);
    }

}
