package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foragentss.auth.models.CardPrice;
import com.example.foragentss.auth.models.CardRequest;
import com.example.foragentss.auth.repository.CardPriceRepository;
import com.example.foragentss.auth.utils.ApiResponse;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

public class CardPriceViewModel extends AndroidViewModel {
    private CardPriceRepository cardPriceRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    public CardPriceViewModel(@NonNull Application application) {
        super(application);

        cardPriceRepository = new CardPriceRepository();
    }

    public Call<ArrayList<CardPrice>> index(){
        return cardPriceRepository.index();
    }

    public MutableLiveData<ApiResponse> storeResponse() {
        return responseLiveData;
    }

    public void store(CardPrice cardPrice){
        Observable.just(cardPriceRepository.store(cardPrice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }

    public void update(int id,CardPrice cardPrice){
        Observable.just(cardPriceRepository.update(id,cardPrice)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }
}
