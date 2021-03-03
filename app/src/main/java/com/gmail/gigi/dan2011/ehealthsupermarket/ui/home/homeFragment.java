package com.gmail.gigi.dan2011.ehealthsupermarket.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.gmail.gigi.dan2011.ehealthsupermarket.activity_productview;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class homeFragment extends Fragment {

    private homeViewModel homeViewModel;
    private FirebaseAuth mauth;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //Get Auth Firebase reference
       mauth = FirebaseAuth.getInstance();
        //Get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();




        homeViewModel = new ViewModelProvider(this).get(homeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        /*  Button to view the activity that contains the product information.*/
        ImageButton product = (ImageButton) root.findViewById(R.id.imagebuttonproduct2);
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), activity_productview.class);
                startActivity(in);
            }
        });

        return root;
    }


}
