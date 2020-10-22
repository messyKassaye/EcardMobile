package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.foragentss.auth.models.Address;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.repository.CardRequestRepository;
import com.example.foragentss.auth.response.CardRequestResponse;
import com.example.foragentss.auth.utils.ApiResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CardRequestViewModel extends AndroidViewModel {
    private CardRequestRepository cardRequestRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    public CardRequestViewModel(@NonNull Application application) {
        super(application);

        cardRequestRepository = new CardRequestRepository();
    }

    public LiveData<CardRequestResponse> show(String status){
        return  cardRequestRepository.show(status);
    }

    public MutableLiveData<ApiResponse> storeResponse() {
        return responseLiveData;
    }

    public void store(CardRequest cardRequest){
        Observable.just(cardRequestRepository.store(cardRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }

    public void update(int id,String status){
        Observable.just(cardRequestRepository.update(id,status)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }
}
