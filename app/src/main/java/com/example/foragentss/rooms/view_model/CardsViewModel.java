package com.example.foragentss.rooms.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.entity.Card;
import com.example.foragentss.rooms.respository.CardsRepository;
import com.example.foragentss.rooms.respository.UsersRepository;

import java.util.List;

public class CardsViewModel extends AndroidViewModel {

    private CardsRepository cardsRepository;

    public CardsViewModel(@NonNull Application application) {
        super(application);
        cardsRepository = new CardsRepository(application);
    }

    public LiveData<List<Card>> showSell(String date,String type){
        return cardsRepository.showSell(date,type);
    }
}
