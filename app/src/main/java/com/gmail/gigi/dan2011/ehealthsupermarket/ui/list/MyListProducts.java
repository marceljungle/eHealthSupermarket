package com.gmail.gigi.dan2011.ehealthsupermarket.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.appcompat.app.AppCompatActivity;
import com.gmail.gigi.dan2011.ehealthsupermarket.AddProduct2MyList;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * Javadoc comment.
 */
public class MyListProducts extends AppCompatActivity {

  private FloatingActionButton floatingActionButton;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mylist_products);

    //TODO: search in db for the list with the rowitem id? maybe other attributes?
    RowItem item = (RowItem) getIntent().getSerializableExtra("listItem");

    floatingActionButton = findViewById(R.id.fab_mylist_products);
    floatingActionButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), AddProduct2MyList.class);
        intent.putExtra("idList", item.getId());
        startActivity(intent);
      }
    });

  }
}