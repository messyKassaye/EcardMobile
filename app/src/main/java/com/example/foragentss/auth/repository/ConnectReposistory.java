package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.Connect;
import com.example.foragentss.auth.response.ConnectionsResponse;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.ConnectionInterface;

import io.reactivex.Observable;
import retrofit2.Call;

public class ConnectReposistory {
    private ConnectionInterface connectionInterface;

    public ConnectReposistory(){
        connectionInterface = RetrofitRequest.getApiInstance().create(ConnectionInterface.class);

    }

    public Observable<SuccessResponse> connect(Connect connect){
        return connectionInterface.connect(connect);
    }

    public Call<ConnectionsResponse> show(String status){
        return connectionInterface.show(status);
    }
}
