package com.example.foragentss.rooms.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.foragentss.auth.models.Device;
import com.example.foragentss.rooms.entity.DeviceRoom;
import com.example.foragentss.rooms.respository.DeviceRepository;

import java.util.ArrayList;
import java.util.List;

public class DeviceRoomViewModel extends AndroidViewModel {
    private DeviceRepository deviceRepository;
    public DeviceRoomViewModel(@NonNull Application application) {
        super(application);

        deviceRepository = new DeviceRepository(application);
    }
    public LiveData<List<DeviceRoom>> index(String serialNumber){
        return deviceRepository.index(serialNumber);
    }

    public void store(DeviceRoom deviceRoom){
        deviceRepository.insert(deviceRoom);
    }
}
