package com.gmail.gigi.dan2011.ehealthsupermarket.ui.intolerances;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class intolerancesViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public intolerancesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Estas son mis listas");

    }

    public LiveData<String> getText() {
        return mText;
    }
}
