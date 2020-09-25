package com.example.foragentss.dashboard.ui.myCustomers;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyCustomersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyCustomersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}