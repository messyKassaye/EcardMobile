package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.foragentss.auth.models.MyCardRequestData;
import com.example.foragentss.auth.response.MyCardRequestRepository;

import retrofit2.Call;

public class MyCardRequestViewModel extends AndroidViewModel {
    private MyCardRequestRepository myCardRequestRepository;
    public MyCardRequestViewModel(@NonNull Application application) {
        super(application);

        myCardRequestRepository = new MyCardRequestRepository();
    }

    public Call<MyCardRequestData> index(){
        return myCardRequestRepository.index();
    }
}
