package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.BankAccount;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.BankAccountInterface;

import io.reactivex.Observable;

public class BankAccountRepository {

    private BankAccountInterface bankAccountInterface;

    public BankAccountRepository(){
        bankAccountInterface = RetrofitRequest.getApiInstance().create(BankAccountInterface.class);

    }

    public Observable<SuccessResponse> store(BankAccount bankAccount){
        return bankAccountInterface.store(bankAccount);
    }

    public Observable<SuccessResponse> update(int id,BankAccount bankAccount){
        return bankAccountInterface.update(id,bankAccount.getAccount_number(),bankAccount.getHolder_full_name());
    }
}
