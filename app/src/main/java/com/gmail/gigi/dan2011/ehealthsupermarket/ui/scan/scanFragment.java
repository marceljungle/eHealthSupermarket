package com.gmail.gigi.dan2011.ehealthsupermarket.ui.scan;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.TextureView;
import android.widget.Button;
import android.widget.TextView;

import com.gmail.gigi.dan2011.ehealthsupermarket.AndroidCameraApi;
import com.gmail.gigi.dan2011.ehealthsupermarket.MainActivity;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.gmail.gigi.dan2011.ehealthsupermarket.activity_productview;
import com.google.android.material.button.MaterialButton;

import java.io.File;
import java.io.IOException;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import me.aflak.ezcam.EZCam;
import me.aflak.ezcam.EZCamCallback;

public class scanFragment extends Fragment {

    private MaterialButton qrButton; // or Button type if not working
    private MaterialButton smartButton;
    //private CamPollingThread camPollingThread;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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