package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foragentss.auth.repository.RegionCityRepository;
import com.example.foragentss.auth.repository.RegionCityResponse;

public class RegionCityViewModel extends AndroidViewModel {
    private RegionCityRepository regionCityRepository;
    private LiveData<RegionCityResponse> responseLiveData;
    public RegionCityViewModel(@NonNull Application application) {
        super(application);
        regionCityRepository = new RegionCityRepository();
        responseLiveData = regionCityRepository.index();
    }

    public LiveData<RegionCityResponse> index(){
        return responseLiveData;
    }
}
