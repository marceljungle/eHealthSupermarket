package com.gmail.gigi.dan2011.ehealthsupermarket.ui.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class accountViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public accountViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esta es mi cuenta");

    }

    public LiveData<String> getText() {
        return mText;
    }
}
