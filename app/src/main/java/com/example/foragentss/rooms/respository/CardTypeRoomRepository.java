package com.example.foragentss.rooms.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.ApplicationRoomDatabase;
import com.example.foragentss.rooms.DAO.CardTypeDAO;
import com.example.foragentss.rooms.entity.CardTypeRoom;

import java.util.List;

public class CardTypeRoomRepository {
    private CardTypeDAO cardTypeDAO;

    public CardTypeRoomRepository(Application application){
        ApplicationRoomDatabase applicationRoomDatabase = ApplicationRoomDatabase
                .getDatabase(application);
        cardTypeDAO = applicationRoomDatabase.cardTypeDAO();
    }

    public LiveData<List<CardTypeRoom>> index(){
        return cardTypeDAO.index();
    }

    public void store(CardTypeRoom cardTypeRoom){
        ApplicationRoomDatabase.dbExecutorService.execute(()->{
            cardTypeDAO.store(cardTypeRoom);
        });
    }
}
