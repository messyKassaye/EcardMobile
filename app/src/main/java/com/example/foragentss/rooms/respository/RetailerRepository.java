package com.example.foragentss.rooms.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.ApplicationRoomDatabase;
import com.example.foragentss.rooms.DAO.RetailersDAO;
import com.example.foragentss.rooms.entity.Retailer;

import java.util.List;

public class RetailerRepository {
    private RetailersDAO retailersDAO;

    public RetailerRepository(Application application){
        retailersDAO = ApplicationRoomDatabase.getDatabase(application).retailersDAO();
    }

    public LiveData<List<Retailer>> getRetailer(){
        return retailersDAO.getRetailer();
    }

    public void store(Retailer retailer){
        ApplicationRoomDatabase.dbExecutorService.execute(()->{
            retailersDAO.store(retailer);
        });
    }
}
