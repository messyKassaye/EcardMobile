package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foragentss.auth.repository.MeRepository;
import com.example.foragentss.auth.response.MeResponse;


public class MeViewModel extends AndroidViewModel {
   private MeRepository repository;
   private LiveData<MeResponse> response;
    public MeViewModel(@NonNull Application application) {
        super(application);

        repository = new MeRepository();
        response = repository.me();
    }

    public LiveData<MeResponse> me(){
        return response;
    }
}
