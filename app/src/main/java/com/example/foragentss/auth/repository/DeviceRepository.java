package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.Device;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.DeviceInterface;

import io.reactivex.Observable;

public class DeviceRepository {

    private DeviceInterface deviceInterface;

    public DeviceRepository(){
        deviceInterface = RetrofitRequest.getApiInstance().create(DeviceInterface.class);
    }

    public Observable<SuccessResponse> store(Device device){
        return deviceInterface.store(device);
    }

    public Observable<SuccessResponse> update(int id,Device serial_number){
        return deviceInterface.update(id,serial_number);
    }
}
