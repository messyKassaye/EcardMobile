package com.example.foragentss.rooms.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.entity.CardTypeRoom;
import com.example.foragentss.rooms.respository.CardTypeRoomRepository;

import java.util.List;

public class CardTypeRoomViewModel extends AndroidViewModel {

    private CardTypeRoomRepository cardTypeRoomRepository;
    public CardTypeRoomViewModel(@NonNull Application application) {
        super(application);

        cardTypeRoomRepository = new CardTypeRoomRepository(application);
    }

    public void store(CardTypeRoom cardTypeRoom){
        cardTypeRoomRepository.store(cardTypeRoom);
    }

    public LiveData<List<CardTypeRoom>> index(){
        return cardTypeRoomRepository.index();
    }
}
