package com.example.foragentss.rooms.respository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.foragentss.rooms.ApplicationRoomDatabase;
import com.example.foragentss.rooms.DAO.DeviceDAO;
import com.example.foragentss.rooms.entity.DeviceRoom;
import java.util.List;

public class DeviceRepository {
    private DeviceDAO deviceDAO;

    public DeviceRepository(Application application){
        ApplicationRoomDatabase applicationRoomDatabase = ApplicationRoomDatabase
                .getDatabase(application);
        deviceDAO = applicationRoomDatabase.deviceDAO();

    }


    public LiveData<List<DeviceRoom>> index(String serialNumber){
        return  deviceDAO.index(serialNumber);
    }

    public void insert(DeviceRoom deviceRoom){
        ApplicationRoomDatabase.dbExecutorService.execute(()->{
            deviceDAO.store(deviceRoom);
        });
    }

}
