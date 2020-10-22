package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foragentss.auth.models.Connect;
import com.example.foragentss.auth.models.Notification;
import com.example.foragentss.auth.repository.NotificationRepository;
import com.example.foragentss.auth.response.NotificationResponse;
import com.example.foragentss.auth.utils.ApiResponse;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

public class NotificationViewModel extends AndroidViewModel {
    private NotificationRepository notificationRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    public NotificationViewModel(@NonNull Application application) {
        super(application);

        notificationRepository = new NotificationRepository();
    }

    public Call<NotificationResponse> index(){
        return notificationRepository.index();
    }

    public MutableLiveData<ApiResponse> storeResponse() {
        return responseLiveData;
    }


    public void update(int id,int status){
        Observable.just(notificationRepository.udpate(id,status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }
}
