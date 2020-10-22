package com.example.foragentss.rooms.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foragentss.rooms.entity.UserRoom;

import java.util.List;

@Dao
public interface UsersDAO {

    @Query("select * from users")
    public LiveData<List<UserRoom>> index();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void store(UserRoom userRoom);

    @Query("select * from users where phone=:phone and password=:password")
    public LiveData<List<UserRoom>> showUser(String phone, String password);

    @Query("select * from users where phone=:phone")
    public LiveData<List<UserRoom>> me(String phone);
}
