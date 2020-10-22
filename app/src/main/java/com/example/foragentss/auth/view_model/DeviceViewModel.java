package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foragentss.auth.models.BankAccount;
import com.example.foragentss.auth.models.Device;
import com.example.foragentss.auth.repository.DeviceRepository;
import com.example.foragentss.auth.utils.ApiResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class DeviceViewModel extends AndroidViewModel {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    private DeviceRepository deviceRepository;

    public DeviceViewModel(@NonNull Application application) {
        super(application);

        deviceRepository = new DeviceRepository();
    }

    public MutableLiveData<ApiResponse> storeResponse() {
        return responseLiveData;
    }


    public void store(Device device){
        Observable.just(deviceRepository.store(device)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }

    public void update(int id,Device device){
        Observable.just(deviceRepository.update(id,device)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }


}
