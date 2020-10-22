package com.example.foragentss.rooms.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.entity.UserRoom;
import com.example.foragentss.rooms.respository.UsersRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UsersRepository usersRepository;

    public UserViewModel(@NonNull Application application) {
        super(application);
        usersRepository = new UsersRepository(application);
    }


    public LiveData<List<UserRoom>> index(){
        return  usersRepository.index();
    }

    public void store(UserRoom userRoom){
        usersRepository.store(userRoom);
    }

   public LiveData<List<UserRoom>> showUser(String phone, String password){
        return usersRepository.showUser(phone,password);
   }

   public LiveData<List<UserRoom>> me(String phone){
        return  usersRepository.me(phone);
   }
}
