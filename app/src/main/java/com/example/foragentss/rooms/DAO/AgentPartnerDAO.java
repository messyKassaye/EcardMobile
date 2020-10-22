package com.example.foragentss.rooms.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foragentss.rooms.entity.AgentAndPartner;
import com.example.foragentss.rooms.entity.Card;

import java.util.List;

@Dao
public interface AgentPartnerDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void store(AgentAndPartner card);


    @Query("select * from agentPartner")
    public LiveData<List<AgentAndPartner>> index();

}
