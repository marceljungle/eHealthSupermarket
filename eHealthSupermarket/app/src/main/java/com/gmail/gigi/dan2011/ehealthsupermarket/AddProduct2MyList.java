package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gigi.dan2011.ehealthsupermarket.MyListAdapter.ClickListener;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import com.gmail.gigi.dan2011.ehealthsupermarket.ui.list.RowItem;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddProduct2MyList extends AppCompatActivity {

  private RecyclerView listShow;
  private FirebaseFirestore db = FirebaseFirestore.getInstance();
  private MyListAdapter myListAdapter;
  private SearchView searchView;
  private List<Product> productList = new ArrayList<>();
  private FirebaseUser user;
  private String idList;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_product2_my_list);
    user = FirebaseAuth.getInstance().getCurrentUser();
    idList = (String) getIntent().getSerializableExtra("idList");
    importProducts(this);
    listShow = findViewById(R.id.listshowAdd2MyList);
    listShow.setHasFixedSize(true);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    listShow.setLayoutManager(linearLayoutManager);
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.searchfile, menu);
    final MenuItem myActionMenuItem = menu.findItem(R.id.search);
    searchView = (SearchView) myActionMenuItem.getActionView();
    searchView.setFocusable(true);
    searchView.setIconifiedByDefault(false);
    searchView.requestFocus();
    searchView.setOnQueryTextListener(new OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        if (!searchView.isIconified()) {
          searchView.setIconified(true);
        }
        myActionMenuItem.collapseActionView();
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        final List<Product> filtermodelist = filter(productList,
            newText);
        myListAdapter.setfilter(filtermodelist);
        return true;
      }
    });
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public void onBackPressed() {
    finish();
    super.onBackPressed();
  }

  private List<Product> filter(List<Product> pl, String query) {
    query = query.toLowerCase();
    final List<Product> filteredModeList = new ArrayList<>();
    for (Product model : pl) {
      final String text = model.getProduct_name().toLowerCase();
      if (text.startsWith(query)) {
        filteredModeList.add(model);
      }
    }
    if (query.length() > 2) {
      filteredModeList.add(0,
          new Product("CUSTOM","Añadir \"" + query + "\"", query, null, null, null, null, null, null, null, null, null,
              null,
              null));
    }

    //productList = filteredModeList;
    return filteredModeList;
  }

  /* Get all products from db except user's */
  private void importProducts(Context context) {
    final ObjectMapper mapper = new ObjectMapper();
    db.collection("SHOPPINGLISTS").whereEqualTo("id", idList).get()
        .addOnCompleteListener(
            new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                  Map<String, Object> lists = new HashMap<>();
                  RowItem list = new RowItem();
                  List<Product> productsAlreadyInList = new ArrayList<>();
                  for (QueryDocumentSnapshot document : task.getResult()) {
                    lists = document.getData();
                    list = mapper.convertValue(lists, RowItem.class);
                    list.setSmallImageName(R.drawable.ic_intolerances);
                    productsAlreadyInList = list.getProductsInTheList();
                  }
                  List<Product> finalProductsAlreadyInList = productsAlreadyInList;
                  db.collection("PRODUCTS").get()
                      .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                          if (task.isSuccessful()) {
                            final ObjectMapper mapper = new ObjectMapper();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                              Map<String, Object> objectMap = document.getData();
                                Product product = mapper.convertValue(objectMap, Product.class);
                                if (product.getProduct_name() != null
                                    && product.getProduct_name() != "") {
                                  if (!finalProductsAlreadyInList.contains(product)) {
                                    productList.add(product);
                                  }
                                }
                            }
                            myListAdapter = new MyListAdapter(productList,
                                context, user, db);

                            myListAdapter.setOnItemClickListener(new ClickListener() {
                              @Override
                              public void onItemClick(int position, View v) {
                                Product productClicked = myListAdapter.getProduct(position);
                                if(productClicked.getProduct_id() == "CUSTOM") {
                                  productClicked.setProduct_name(productClicked.getGeneric_name());
                                }
                                db.collection("SHOPPINGLISTS").document(idList).update("productsInTheList",
                                    FieldValue.arrayUnion(productClicked));

                                //import again all the data
                                //importProducts(context);
                                finish();
                              }
                            });
                            listShow.setAdapter(myListAdapter);
                            myListAdapter.notifyDataSetChanged();

                          }
                        }
                      });
                }
              }
            });
  }
}