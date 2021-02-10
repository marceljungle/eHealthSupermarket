package com.gmail.gigi.dan2011.ehealthsupermarket.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class homeViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public homeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esto es el inicio");

    }

    public LiveData<String> getText() {
        return mText;
    }
}
