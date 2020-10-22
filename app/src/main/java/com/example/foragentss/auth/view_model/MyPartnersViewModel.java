package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.foragentss.auth.models.User;
import com.example.foragentss.auth.repository.MyPartnersRepository;
import com.example.foragentss.auth.response.ConnectionsResponse;

import java.util.ArrayList;

import retrofit2.Call;

public class MyPartnersViewModel extends AndroidViewModel {

    private MyPartnersRepository myPartnersRepository;
    public MyPartnersViewModel(@NonNull Application application) {
        super(application);

        myPartnersRepository = new MyPartnersRepository();
    }

    public Call<ConnectionsResponse> show(int status){
        return myPartnersRepository.show(status);
    }
}
