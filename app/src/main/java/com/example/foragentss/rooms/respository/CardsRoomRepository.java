package com.example.foragentss.rooms.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.ApplicationRoomDatabase;
import com.example.foragentss.rooms.DAO.CardsDAO;
import com.example.foragentss.rooms.entity.Card;

import java.util.List;

public class CardsRoomRepository {
    private CardsDAO cardsDAO;

    public CardsRoomRepository(Application application){
        ApplicationRoomDatabase applicationRoomDatabase = ApplicationRoomDatabase.getDatabase(application);

        cardsDAO = applicationRoomDatabase.cardsDAO();
    }

    public LiveData<List<Card>> index(){
       return cardsDAO.index();
    }
    public LiveData<List<Card>> update(int id){
        return cardsDAO.update(id);
    }

    public void store(Card card){
        ApplicationRoomDatabase.dbExecutorService.execute(()->{
            cardsDAO.store(card);
        });
    }
    public LiveData<List<Card>> showSell(String date,int type){
        return cardsDAO.showSell(date,type);
    }

    public LiveData<List<Card>> show(int cardType){
        return cardsDAO.showCard(cardType);
    }
}
