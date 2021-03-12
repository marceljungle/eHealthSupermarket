package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

public class activity_products_listview extends AppCompatActivity {

    private GridView gridView;
    private Product_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_listview);

        gridView = (GridView) findViewById(R.id.grid);
        adapter = new Product_Adapter(this);
        gridView.setAdapter(adapter);
    }

}