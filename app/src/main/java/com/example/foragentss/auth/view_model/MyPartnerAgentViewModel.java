package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.foragentss.auth.models.MyPartner;
import com.example.foragentss.auth.repository.MyPartnerAgentRepository;
import com.example.foragentss.auth.response.AgentPartnerResponse;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.utils.ApiResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

public class MyPartnerAgentViewModel extends AndroidViewModel {
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    private MyPartnerAgentRepository myPartnerAgentRepository;
    public MyPartnerAgentViewModel(@NonNull Application application) {
        super(application);

        myPartnerAgentRepository = new MyPartnerAgentRepository();
    }

    public Call<AgentPartnerResponse> index(String path){
        return myPartnerAgentRepository.index(path);
    }
    public MutableLiveData<ApiResponse> storeResponse() {
        return responseLiveData;
    }

    public void store(String path,MyPartner myPartner){
        Observable.just(myPartnerAgentRepository.store(path,myPartner)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }

    public void update(String path,int id,MyPartner myPartner){
        Observable.just(myPartnerAgentRepository.update(path,id,myPartner)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe((d) -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        throwable -> responseLiveData.setValue(ApiResponse.error(throwable))
                ));
    }
}
