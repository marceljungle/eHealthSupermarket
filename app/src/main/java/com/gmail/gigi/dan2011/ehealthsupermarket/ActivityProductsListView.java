package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.os.Bundle;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Javadoc comment.
 */
public class ActivityProductsListView extends AppCompatActivity {

  private GridView gridView;
  private ProductAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_products_listview);

    gridView = (GridView) findViewById(R.id.grid);
    adapter = new ProductAdapter(this);
    gridView.setAdapter(adapter);
  }

}