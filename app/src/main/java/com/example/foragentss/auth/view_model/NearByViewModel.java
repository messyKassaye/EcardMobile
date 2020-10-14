package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.foragentss.auth.repository.NearByReposistory;
import com.example.foragentss.auth.response.NearByResponse;

import retrofit2.Call;

public class NearByViewModel extends AndroidViewModel {
    private NearByReposistory nearByReposistory;
    public NearByViewModel(@NonNull Application application) {
        super(application);

        nearByReposistory = new NearByReposistory();
    }

    public Call<NearByResponse> index(){
        return nearByReposistory.index();
    }
}
