package com.example.foragentss.auth.repository;

import com.example.foragentss.auth.models.Address;
import com.example.foragentss.auth.response.SuccessResponse;
import com.example.foragentss.auth.retrofit.RetrofitRequest;
import com.example.foragentss.auth.retrofit.interfaces.AddressInterface;

import io.reactivex.Observable;

public class AddressRepository {
    private AddressInterface addressInterface;

    public AddressRepository(){
        addressInterface = RetrofitRequest.getApiInstance().create(AddressInterface.class);

    }

    public Observable<SuccessResponse> store(Address address){
       return addressInterface.store(address);
    }
}
