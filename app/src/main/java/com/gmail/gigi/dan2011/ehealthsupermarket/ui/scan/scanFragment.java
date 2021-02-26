package com.gmail.gigi.dan2011.ehealthsupermarket.ui.scan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.gigi.dan2011.ehealthsupermarket.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

public class scanFragment extends Fragment {

    private scanViewModel scanViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        scanViewModel = new ViewModelProvider(this).get(scanViewModel.class);
        View root = inflater.inflate(R.layout.fragment_scan, container, false);
        return root;
    }
}
