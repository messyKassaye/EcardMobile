package com.example.foragentss.auth.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foragentss.auth.response.CardRequestResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.RegionCityInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegionCityRepository {

    private RegionCityInterface regionCityInterface;

    public RegionCityRepository() {
        regionCityInterface = RetrofitRequest.getApiInstance().create(RegionCityInterface.class);
    }

    public LiveData<RegionCityResponse> index(){
        final MutableLiveData<RegionCityResponse> data = new MutableLiveData<>();
        regionCityInterface.index().enqueue(new Callback<RegionCityResponse>() {
            @Override
            public void onResponse(Call<RegionCityResponse> call, Response<RegionCityResponse> response) {
                if(response.body()!=null){
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<RegionCityResponse> call, Throwable t) {
                data.setValue(null);

            }
        });
        return data;
    }
}
