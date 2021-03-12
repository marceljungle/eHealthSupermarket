package com.gmail.gigi.dan2011.ehealthsupermarket.ui.scan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.gmail.gigi.dan2011.ehealthsupermarket.AndroidCameraApi;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.google.android.material.button.MaterialButton;

/**
 * Javadoc comment.
 */
public class ScanFragment extends Fragment {

  private MaterialButton qrButton; // or Button type if not working
  private MaterialButton smartButton;
  //private CamPollingThread camPollingThread;

  /**
   * Javadoc comment.
   */
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_scan, container, false);
    qrButton = root.findViewById(R.id.qrButton);
    smartButton = root.findViewById(R.id.smartButton);
    qrButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent act = new Intent(getActivity(), AndroidCameraApi.class);
        startActivity(act);
      }
    });
    smartButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent act = new Intent(getActivity(), AndroidCameraApi.class);
        startActivity(act);
      }
    });
    return root;
  }
}