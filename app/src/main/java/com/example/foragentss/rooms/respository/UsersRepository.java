package com.example.foragentss.rooms.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.ApplicationRoomDatabase;
import com.example.foragentss.rooms.DAO.UsersDAO;
import com.example.foragentss.rooms.entity.User;

import java.util.List;

public class UsersRepository {
    private UsersDAO usersDAO;

    public UsersRepository(Application application){
        ApplicationRoomDatabase applicationRoomDatabase = ApplicationRoomDatabase.getDatabase(application);

        usersDAO = applicationRoomDatabase.usersDAO();
    }

    public LiveData<List<User>> index(){
        return usersDAO.index();
    }

    public void store(User user){
        ApplicationRoomDatabase.dbExecutorService.execute(()->{
            usersDAO.store(user);
        });
    }

    public LiveData<List<User>> showUser(String phone, String password){
        return usersDAO.showUser(phone,password);
    }

    public LiveData<List<User>> me(String phone){
        return usersDAO.me(phone);
    }
}
