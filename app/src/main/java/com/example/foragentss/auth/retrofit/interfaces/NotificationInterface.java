package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.Notification;
import com.example.foragentss.auth.response.NotificationResponse;
import com.example.foragentss.auth.response.SuccessResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.Path;

public interface NotificationInterface {

    @GET("notification")
    Call<NotificationResponse> index();

    @FormUrlEncoded
    @PATCH("notification/{id}")
    Observable<SuccessResponse> update(@Path("id")int id, @Field("status")int status);
}
