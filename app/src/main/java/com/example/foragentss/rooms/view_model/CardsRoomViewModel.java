package com.example.foragentss.rooms.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.entity.Card;
import com.example.foragentss.rooms.respository.CardsRoomRepository;

import java.util.List;

public class CardsRoomViewModel extends AndroidViewModel {

    private CardsRoomRepository cardsRoomRepository;

    public CardsRoomViewModel(@NonNull Application application) {
        super(application);
        cardsRoomRepository = new CardsRoomRepository(application);
    }

    public LiveData<List<Card>> index(){
        return cardsRoomRepository.index();
    }

    public LiveData<List<Card>> update(int id){
        return cardsRoomRepository.update(id);
    }

    public void store(Card card){
        cardsRoomRepository.store(card);
    }
    public LiveData<List<Card>> showSell(String date,int type){
        return cardsRoomRepository.showSell(date,type);
    }

    public LiveData<List<Card>> showCard(int cardTypeId){
        return cardsRoomRepository.show(cardTypeId);
    }
}
