package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.PaymentType;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.PaymentTypesInterface;

import java.util.ArrayList;

import retrofit2.Call;

public class PaymentTypeRepository {
    private PaymentTypesInterface paymentTypesInterface;

    public PaymentTypeRepository(){
        paymentTypesInterface = RetrofitRequest.getApiInstance().create(PaymentTypesInterface.class);
    }

    public Call<ArrayList<PaymentType>> index(){
        return paymentTypesInterface.index();
    }
}
