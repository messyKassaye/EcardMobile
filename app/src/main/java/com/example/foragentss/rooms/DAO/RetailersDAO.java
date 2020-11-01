package com.example.foragentss.rooms.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foragentss.rooms.entity.Retailer;

import java.util.List;

@Dao
public interface RetailersDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void  store(Retailer retailer);

    @Query("select * from retailers")
    public LiveData<List<Retailer>> getRetailer();
}
