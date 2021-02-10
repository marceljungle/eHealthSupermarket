package com.gmail.gigi.dan2011.ehealthsupermarket.ui.scan;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class scanViewModel extends ViewModel {

    private MutableLiveData<String> mText;


    public scanViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es el apartado de escaneo");

    }

    public LiveData<String> getText() {
        return mText;
    }
}
