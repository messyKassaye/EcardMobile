package com.example.foragentss.rooms.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foragentss.rooms.entity.CardTypeRoom;

import java.util.List;

@Dao
public interface CardTypeDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void store(CardTypeRoom cardType);


    @Query("select * from cardTypes")
    public LiveData<List<CardTypeRoom>> index();
}
