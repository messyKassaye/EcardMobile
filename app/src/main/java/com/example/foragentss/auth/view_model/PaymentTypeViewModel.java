package com.example.foragentss.auth.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.foragentss.auth.models.PaymentType;
import com.example.foragentss.auth.repository.PaymentTypeRepository;

import java.util.ArrayList;

import retrofit2.Call;

public class PaymentTypeViewModel extends AndroidViewModel {

    private PaymentTypeRepository paymentTypeRepository;
    public PaymentTypeViewModel(@NonNull Application application) {
        super(application);

        paymentTypeRepository = new PaymentTypeRepository();
    }

    public Call<ArrayList<PaymentType>> index(){
        return paymentTypeRepository.index();
    }
}
