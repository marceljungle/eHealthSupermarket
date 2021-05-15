package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Featured;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import com.google.android.gms.common.Feature;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.common.StringUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Javadoc comment.
 */
public class ActivityProductsListView extends AppCompatActivity {

  private GridView gridView;
  private ProductAdapter adapter;
  private ArrayList<Product> arrayList = new ArrayList<>();
  private FirebaseFirestore db = FirebaseFirestore.getInstance();
  private FirebaseUser user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_products_listview);

    // calling the action bar
    ActionBar actionBar = getSupportActionBar();

    // showing the back button in action bar
    actionBar.setDisplayHomeAsUpEnabled(true);

    // disabling title in the actionbar
    actionBar.setDisplayShowTitleEnabled(false);

    // Get current user
    user = FirebaseAuth.getInstance().getCurrentUser();

    //Get the type of the view to show
    String type = (String) getIntent().getSerializableExtra("show");

    /**
     * If type is allProducts --> show all products in the view
     * If type is likedProducts --> show liked products in the view
     * If type is basedInTheIntolerances --> show products based in the intolerances of the user
     */

    if (type!=null && type.equals("likedProducts")) {
      actionToLikedproducts(user);
    } else if (type!=null && type.equals("allProducts")) {
      actionToAllProducts(user);
    }else {
      Bundle args = getIntent().getBundleExtra("featuredProducts");
      ArrayList<Product> products = (ArrayList<Product>) args.getSerializable("ARRAYLIST");
      arrayList = products;
      actionToFeaturedProducts();
    }

  }

  private void actionToFeaturedProducts() {
    gridView = (GridView) findViewById(R.id.grid);
    adapter = new ProductAdapter(ActivityProductsListView.this, arrayList);
    gridView.setAdapter(adapter);
    //click on product
    //View root = getLayoutInflater().inflate(R.layout.grid_item_product, null);
    gridView = findViewById(R.id.grid);
    Intent intent = new Intent(ActivityProductsListView.this, ActivityProductView.class);
    gridView.setOnItemClickListener(new OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        adapter.getView(position, view, parent);
        List<Product> product1 = arrayList.stream()
            .filter(product -> product.getProduct_id() == adapter.productGetId()).collect(
                Collectors.toList());
        if (!product1.isEmpty()) {
          intent.putExtra("product", product1.get(0));
        }
        //System.out.println("---------------------------------" + product1.get(0).getImage());
        startActivity(intent);
      }
    });
  }

  // this event will enable the back
  // function to the button on press
  @Override
  public boolean onOptionsItemSelected(@NonNull MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        this.finish();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onBackPressed() {
    super.onBackPressed();
    finish();
  }

  /**
   * Function to show the liked products in the view.
   */

  private void actionToLikedproducts(FirebaseUser user) {
    //Get all liked products of firebase database
    String userUid = user.getUid();
    final ObjectMapper mapper = new ObjectMapper();
    db.collection("USERS").document(userUid).get().addOnCompleteListener(
        new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            List<Map<String, Object>> favProducts = (List) task.getResult().getData()
                .get("liked_products");
            for (Map<String, Object> mapProd : favProducts) {
              arrayList.add(mapper.convertValue(mapProd, Product.class));
            }
            
            gridView = (GridView) findViewById(R.id.grid);
            adapter = new ProductAdapter(ActivityProductsListView.this, arrayList);
            gridView.setAdapter(adapter);
            //click on product
            //View root = getLayoutInflater().inflate(R.layout.grid_item_product, null);
            gridView = findViewById(R.id.grid);
            Intent intent = new Intent(ActivityProductsListView.this,
                ActivityProductView.class);
            gridView.setOnItemClickListener(new OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.getView(position, view, parent);
                List<Product> product1 = arrayList.stream()
                    .filter(product -> product.getProduct_id() == adapter.productGetId())
                    .collect(
                        Collectors.toList());
                if (!product1.isEmpty()) {
                  intent.putExtra("product", product1.get(0));
                }
                //System.out.println("---------------------------------" + product1.get(0).getImage());
                startActivity(intent);
              }
            });
          }
        });
  }

  /**
   * Function to show all products in the view.
   */
  private void actionToAllProducts(FirebaseUser user) {
    //Get all liked products of firebase database
    String userUid = user.getUid();
    final ObjectMapper mapper = new ObjectMapper();

    db.collection("PRODUCTS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
      @Override
      public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
          Map<String, Object> productmap = new HashMap<>();
          Product product = new Product();
          for (QueryDocumentSnapshot document : task.getResult()) {
            productmap = document.getData();
            try {
              product = mapper.convertValue(productmap, Product.class);
            } catch (Exception e) {
            }
            if (product.getProduct_name() != null && product.getProduct_name() != "") {
              arrayList.add(product);
            }
          }
        }
        gridView = (GridView) findViewById(R.id.grid);
        adapter = new ProductAdapter(ActivityProductsListView.this, arrayList);
        gridView.setAdapter(adapter);
        //click on product
        //View root = getLayoutInflater().inflate(R.layout.grid_item_product, null);
        gridView = findViewById(R.id.grid);
        Intent intent = new Intent(ActivityProductsListView.this, ActivityProductView.class);
        gridView.setOnItemClickListener(new OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            adapter.getView(position, view, parent);
            List<Product> product1 = arrayList.stream()
                .filter(product -> product.getProduct_id() == adapter.productGetId()).collect(
                    Collectors.toList());
            if (!product1.isEmpty()) {
              intent.putExtra("product", product1.get(0));
            }
            //System.out.println("---------------------------------" + product1.get(0).getImage());
            startActivity(intent);
          }
        });
      }
    });


  }
}