package com.gmail.gigi.dan2011.ehealthsupermarket;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

public class activity_productview extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productview);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println(item.getItemId());
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return true;
    }
}