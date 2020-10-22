package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.foragentss.auth.repository.MyRetailersRepository;
import com.example.foragentss.auth.response.ConnectionsResponse;

import retrofit2.Call;

public class MyRetailerViewModel extends AndroidViewModel {

    private MyRetailersRepository myRetailersRepository;
    public MyRetailerViewModel(@NonNull Application application) {
        super(application);

        myRetailersRepository = new MyRetailersRepository();
    }

    public Call<ConnectionsResponse> show(int status){
        return myRetailersRepository.show(status);
    }
}
