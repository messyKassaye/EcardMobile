package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foragentss.auth.models.Address;
import com.example.foragentss.auth.models.Complain;
import com.example.foragentss.auth.repository.ComplainRepository;
import com.example.foragentss.auth.utils.ApiResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ComplainViewModel extends AndroidViewModel {
    private ComplainRepository complainRepository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    public ComplainViewModel(@NonNull Application application) {
        super(application);

        complainRepository = new ComplainRepository();
    }

    public MutableLiveData<ApiResponse> storeResponse() {
        return responseLiveData;
    }


    public void store(Complain complain){
        Observable.just(complainRepository.store(complain)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }
}
