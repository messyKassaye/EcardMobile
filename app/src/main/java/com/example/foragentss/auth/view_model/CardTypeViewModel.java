package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.foragentss.auth.models.CardType;
import com.example.foragentss.auth.repository.CardTypeRespository;
import com.example.foragentss.auth.response.CardTypeResponse;
import com.example.foragentss.auth.retrofit.interfaces.CardTypeInterface;

import java.util.ArrayList;

import retrofit2.Call;

public class CardTypeViewModel extends AndroidViewModel {
    private CardTypeRespository cardTypeRespository;
    public CardTypeViewModel(@NonNull Application application) {
        super(application);

        cardTypeRespository = new CardTypeRespository();
    }

    public Call<ArrayList<CardType>> index(){
        return  cardTypeRespository.index();
    }
}
