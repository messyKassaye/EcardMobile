package com.example.foragentss.rooms.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.foragentss.rooms.entity.User;

import java.util.List;

@Dao
public interface UsersDAO {

    @Query("select * from users")
    public LiveData<List<User>> index();
    @Insert
    public void store(User user);

    @Query("select * from users where phone=:phone and password=:password")
    public LiveData<List<User>> showUser(String phone, String password);

    @Query("select * from users where phone=:phone")
    public LiveData<List<User>> me(String phone);
}
