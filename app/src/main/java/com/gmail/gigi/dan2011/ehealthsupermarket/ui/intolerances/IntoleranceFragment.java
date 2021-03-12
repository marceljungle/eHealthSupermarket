package com.gmail.gigi.dan2011.ehealthsupermarket.ui.intolerances;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;

/**
 * Javadoc comment.
 */
public class IntoleranceFragment extends Fragment {

  private IntoleranceViewModel intoleranceViewModel;

  /**
   * Javadoc comment.
   */
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    intoleranceViewModel = new ViewModelProvider(this).get(IntoleranceViewModel.class);
    View root = inflater.inflate(R.layout.fragment_intolerances, container, false);

    return root;
  }
}
