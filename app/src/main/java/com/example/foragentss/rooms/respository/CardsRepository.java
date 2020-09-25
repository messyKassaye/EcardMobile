package com.example.foragentss.rooms.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.ApplicationRoomDatabase;
import com.example.foragentss.rooms.DAO.CardsDAO;
import com.example.foragentss.rooms.entity.Card;

import java.util.List;

public class CardsRepository {
    private CardsDAO cardsDAO;

    public CardsRepository(Application application){
        ApplicationRoomDatabase applicationRoomDatabase = ApplicationRoomDatabase.getDatabase(application);

        cardsDAO = applicationRoomDatabase.cardsDAO();
    }

    public LiveData<List<Card>> showSell(String date,String type){
        return cardsDAO.showSell(date,type);
    }
}
