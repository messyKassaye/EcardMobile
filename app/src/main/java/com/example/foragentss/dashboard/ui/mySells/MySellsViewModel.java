package com.example.foragentss.dashboard.ui.mySells;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MySellsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MySellsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is tools fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}