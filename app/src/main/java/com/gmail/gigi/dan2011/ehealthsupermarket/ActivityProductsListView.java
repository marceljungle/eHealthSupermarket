package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
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
  private FirebaseAuth mauth;
  private FirebaseFirestore db = FirebaseFirestore.getInstance();
  private FirebaseUser user;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_products_listview);

    // Get Auth Firebase reference
    mauth = FirebaseAuth.getInstance();
    // Get current user
    user = FirebaseAuth.getInstance().getCurrentUser();

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