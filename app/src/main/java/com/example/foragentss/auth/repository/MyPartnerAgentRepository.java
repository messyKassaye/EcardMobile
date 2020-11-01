package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.MyPartner;
import com.example.foragentss.auth.response.AgentPartnerResponse;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.MyPartnerAgentInterface;
import com.example.foragentss.auth.retrofit.interfaces.MyPartnersInterface;

import io.reactivex.Observable;
import retrofit2.Call;

public class MyPartnerAgentRepository {
    private MyPartnerAgentInterface myPartnersInterface;

    public MyPartnerAgentRepository(){
        myPartnersInterface = RetrofitRequest.getApiInstance().create(MyPartnerAgentInterface.class);
    }

    public Call<AgentPartnerResponse> index(String path){
        return myPartnersInterface.index(path);
    }

    public Observable<SuccessResponse> store(String path, MyPartner myPartner){
        return myPartnersInterface.store(path,myPartner);
    }

    public Observable<SuccessResponse> update(String path, int id,MyPartner myPartner){
        return myPartnersInterface.update(path,id,myPartner);
    }
}
