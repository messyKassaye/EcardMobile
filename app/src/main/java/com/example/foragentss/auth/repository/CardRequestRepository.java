package com.example.foragentss.auth.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foragentss.auth.models.Address;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.response.CardRequestResponse;
import com.example.foragentss.auth.response.MeResponse;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.CardRequestInterface;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CardRequestRepository {
    private CardRequestInterface cardRequestInterface;

    public CardRequestRepository() {
        cardRequestInterface = RetrofitRequest.getApiInstance().create(CardRequestInterface.class);

    }

    public LiveData<CardRequestResponse> show(String status){
        final MutableLiveData<CardRequestResponse> data = new MutableLiveData<>();
        cardRequestInterface.show(status).enqueue(new Callback<CardRequestResponse>() {
            @Override
            public void onResponse(Call<CardRequestResponse> call, Response<CardRequestResponse> response) {
                if(response.body()!=null){
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<CardRequestResponse> call, Throwable t) {
                data.setValue(null);

            }
        });
        return data;
    }

    public Observable<SuccessResponse> store(CardRequest cardRequest){
        return cardRequestInterface.store(cardRequest);
    }

    public Observable<SuccessResponse> update(int id,String status){
        return  cardRequestInterface.update(id,status);
    }
}
