package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.foragentss.auth.models.Bank;
import com.example.foragentss.auth.repository.BankReposistory;
import com.example.foragentss.auth.response.BankResponse;

import java.util.ArrayList;

import retrofit2.Call;

public class BanksViewModel extends AndroidViewModel {

    private BankReposistory bankReposistory;
    public BanksViewModel(@NonNull Application application) {
        super(application);

        bankReposistory = new BankReposistory();
    }

    public Call<BankResponse> index(){
        return bankReposistory.index();
    }
}
