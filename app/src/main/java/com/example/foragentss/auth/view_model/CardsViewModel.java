package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.foragentss.auth.repository.CardsRepository;
import com.example.foragentss.auth.response.CardsResponse;

import java.util.ArrayList;

import retrofit2.Call;

public class CardsViewModel extends AndroidViewModel {
    private CardsRepository cardsRepository;
    public CardsViewModel(@NonNull Application application) {
        super(application);

        cardsRepository = new CardsRepository();
    }

    public Call<CardsResponse> index(){
        return cardsRepository.index();
    }
}
