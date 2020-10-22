package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.Notification;
import com.example.foragentss.auth.response.NotificationResponse;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.NotificationInterface;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;

public class NotificationRepository {
    private NotificationInterface notificationInterface;

    public NotificationRepository(){
        notificationInterface = RetrofitRequest.getApiInstance().create(NotificationInterface.class);
    }

    public Call<NotificationResponse> index(){
        return notificationInterface.index();
    }
    public Observable<SuccessResponse> udpate(int id,int status){
        return notificationInterface.update(id,status);
    }
}
