package com.example.foragentss.rooms.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foragentss.rooms.entity.Card;

import java.util.List;

@Dao
public interface CardsDAO {

    @Query("select * from cards")
    public LiveData<List<Card>> index();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void store(Card card);

    @Query("select * from cards where sellsDate=:date and card_type_id=:type and status='soled'")
    public LiveData<List<Card>> showSell(String date,int type);

    @Query("select * from cards where card_type_id=:cardTypeId")
    public LiveData<List<Card>> showCard(int cardTypeId);

    @Query("select * from cards where id=:id")
    public LiveData<List<Card>> update(int id);


    @Query("select * from cards where card_type_id=:cardTypeId and status=:status LIMIT 1")
    public LiveData<List<Card>> getCardToSell(int cardTypeId,String status);
}
