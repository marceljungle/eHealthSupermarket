package com.gmail.gigi.dan2011.ehealthsupermarket.sections.account;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Javadoc comment.
 */
public class AccountViewModel extends ViewModel {

  private MutableLiveData<String> mutText;

  /**
   * Javadoc comment.
   */
  public AccountViewModel() {
    mutText = new MutableLiveData<>();
    mutText.setValue("Esta es mi cuenta");

  }

  public LiveData<String> getText() {
    return mutText;
  }
}
