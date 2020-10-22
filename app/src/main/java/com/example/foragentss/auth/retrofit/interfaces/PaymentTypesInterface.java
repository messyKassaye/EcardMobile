package com.example.foragentss.auth.retrofit.interfaces;

import com.example.foragentss.auth.models.PaymentType;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PaymentTypesInterface {

    @GET("payment_types")
    Call<ArrayList<PaymentType>> index();
}
