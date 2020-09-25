package com.example.foragentss.rooms.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foragentss.rooms.entity.Card;

import java.util.List;

@Dao
public interface CardsDAO {

    @Insert
    public void store(Card card);


    @Query("select * from cards where sellsDate=:date and type=:type and status='soled'")
    public LiveData<List<Card>> showSell(String date,String type);
}
