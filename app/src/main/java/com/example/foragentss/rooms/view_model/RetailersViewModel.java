package com.example.foragentss.rooms.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.entity.Retailer;
import com.example.foragentss.rooms.respository.RetailerRepository;

import java.util.List;

public class RetailersViewModel extends AndroidViewModel {
    private RetailerRepository retailerRepository;

    public RetailersViewModel(@NonNull Application application) {
        super(application);

        retailerRepository = new RetailerRepository(application);
    }

    public LiveData<List<Retailer>> getRetailer(){
        return retailerRepository.getRetailer();
    }

    public void store(Retailer retailer){
        retailerRepository.store(retailer);
    }
}
