package com.example.foragentss.rooms.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.ApplicationRoomDatabase;
import com.example.foragentss.rooms.DAO.UsersDAO;
import com.example.foragentss.rooms.entity.UserRoom;

import java.util.List;

public class UsersRepository {
    private UsersDAO usersDAO;

    public UsersRepository(Application application){
        ApplicationRoomDatabase applicationRoomDatabase = ApplicationRoomDatabase.getDatabase(application);

        usersDAO = applicationRoomDatabase.usersDAO();
    }

    public LiveData<List<UserRoom>> index(){
        return usersDAO.index();
    }

    public void store(UserRoom userRoom){
        ApplicationRoomDatabase.dbExecutorService.execute(()->{
            usersDAO.store(userRoom);
        });
    }

    public LiveData<List<UserRoom>> showUser(String phone, String password){
        return usersDAO.showUser(phone,password);
    }

    public LiveData<List<UserRoom>> me(String phone){
        return usersDAO.me(phone);
    }
}
