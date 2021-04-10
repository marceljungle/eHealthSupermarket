package com.gmail.gigi.dan2011.ehealthsupermarket.ui.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gigi.dan2011.ehealthsupermarket.AddProduct2MyList;
import com.gmail.gigi.dan2011.ehealthsupermarket.MyShoppingListProductAdapter;
import com.gmail.gigi.dan2011.ehealthsupermarket.MyShoppingListProductAdapter.ClickListener;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Javadoc comment.
 */
public class MyListProducts extends AppCompatActivity {

  private RecyclerView listShow;
  private FloatingActionButton floatingActionButton;
  private MyShoppingListProductAdapter adapter;
  private FirebaseFirestore db = FirebaseFirestore.getInstance();
  private List<Product> arrayList;
  private ActionBar actionBar;
  private RowItem item;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mylist_products);

    listShow = findViewById(R.id.main_list_mylist);
    listShow.setHasFixedSize(true);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    listShow.setLayoutManager(linearLayoutManager);

    //TODO: search in db for the list with the rowitem id? maybe other attributes?
    item = (RowItem) getIntent().getSerializableExtra("listItem");

    actionBar = getSupportActionBar();
    actionBar.setTitle("Productos en tu lista " + item.getListName());

    floatingActionButton = findViewById(R.id.fab_mylist_products);
    floatingActionButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), AddProduct2MyList.class);
        intent.putExtra("idList", item.getId());
        startActivity(intent);
      }
    });

    importProductsShoppingList(item);

  }

  @Override
  protected void onPostResume() {
    super.onPostResume();
    importProductsShoppingList(item);
  }

  public void importProductsShoppingList(RowItem item) {

    arrayList = new ArrayList<>();

    final ObjectMapper mapper = new ObjectMapper();

    db.collection("SHOPPINGLISTS").document(item.getId()).get().addOnCompleteListener(
        new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            List<Map<String, Object>> products = (List) task.getResult().getData()
                .get("productsInTheList");
            for (Map<String, Object> mapProd : products) {
              arrayList.add(mapper.convertValue(mapProd, Product.class));
            }
            adapter = new MyShoppingListProductAdapter(arrayList, getApplicationContext(), db);
            adapter.setOnItemClickListener(new ClickListener() {
              @Override
              public void onItemClick(int position, View v) {
                db.collection("SHOPPINGLISTS").document(item.getId()).update("productsInTheList",
                    FieldValue.arrayRemove(arrayList.get(position)));
                importProductsShoppingList(item);
              }
            });
            listShow.setAdapter(adapter);
            adapter.notifyDataSetChanged();
          }
        });
  }

  @Override
  public void onBackPressed() {
    finish();
    super.onBackPressed();
  }
}