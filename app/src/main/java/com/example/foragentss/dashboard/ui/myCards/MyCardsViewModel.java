package com.example.foragentss.dashboard.ui.myCards;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MyCardsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MyCardsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}