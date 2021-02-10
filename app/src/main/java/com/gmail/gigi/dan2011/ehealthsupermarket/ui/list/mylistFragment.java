package com.gmail.gigi.dan2011.ehealthsupermarket.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gmail.gigi.dan2011.ehealthsupermarket.DisplayMessageActivity;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class mylistFragment extends Fragment {

    private mylistViewModel mylistViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mylistViewModel = new ViewModelProvider(this).get(mylistViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mylist, container, false);



        //final TextView textView = root.findViewById(R.id.text_mylist);
        //mylistViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return root;
    }




}
