package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.models.Device;
import com.example.foragentss.auth.repository.CardSendRepository;
import com.example.foragentss.auth.utils.ApiResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CardSendViewModel extends AndroidViewModel {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    private CardSendRepository cardSendRepository;
    public CardSendViewModel(@NonNull Application application) {
        super(application);

        cardSendRepository = new CardSendRepository();
    }

    public MutableLiveData<ApiResponse> storeResponse() {
        return responseLiveData;
    }


    public void store(CardRequest cardRequest){
        Observable.just(cardSendRepository.store(cardRequest)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }
}
