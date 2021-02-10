package com.gmail.gigi.dan2011.ehealthsupermarket.ui.list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class mylistViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public mylistViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Estas son mis listas");

    }

    public LiveData<String> getText() {

        return mText;
    }
}
