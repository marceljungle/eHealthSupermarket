package com.gmail.gigi.dan2011.ehealthsupermarket.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.gmail.gigi.dan2011.ehealthsupermarket.ActivityProductsListView;
import com.gmail.gigi.dan2011.ehealthsupermarket.ActivityProductView;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Additive;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Intolerance;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an example of javadoc in a function.
 */
public class HomeFragment extends Fragment {

  private HomeViewModel homeViewModel;
  private ImageView imageProduct;
  private FirebaseAuth mauth;
  private FirebaseFirestore db;
  private Button viewMore1;
  private Logger log = LoggerFactory.getLogger(HomeFragment.class);

  /**
   * Represents an example of javadoc in a function.
   */
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    // Get Auth Firebase reference
    mauth = FirebaseAuth.getInstance();
    // Get current user
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    View root = inflater.inflate(R.layout.fragment_home, container, false);

    /*  Button to view the activity that contains the product information.*/
    ImageButton product = (ImageButton) root.findViewById(R.id.imageProduct);
    ImageView imageProduct = (ImageView) root.findViewById(R.id.imageView_product);

    /*Button to view the activity the contains all products 1*/
    Button viewMore = (Button) root.findViewById(R.id.button_viewMore);
    viewMore.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ActivityProductsListView.class);
        startActivity(intent);
      }
    });





    product.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Intent in = new Intent(getActivity(), ActivityProductView.class);
            startActivity(in);
          }
        });

    db = FirebaseFirestore.getInstance();
    db.collection("ADDITIVES")
        .get()
        .addOnCompleteListener(
            new OnCompleteListener<QuerySnapshot>() {
              @Override
              public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                  final ObjectMapper mapper = new ObjectMapper();
                  for (int i = 0; i < 50; i++) {
                    DocumentSnapshot document = task.getResult().getDocuments().get(0);
                    Map<String, Object> theData = document.getData();
                    theData.put("additiveId", theData.remove("additive_id"));
                    theData.put("additiveName", theData.remove("additive_name"));
                    theData.put("additiveCode", theData.remove("additive_code"));
                    theData.put("additiveDangerLevel", theData.remove("additive_danger_level"));
                    Additive pojo = mapper.convertValue(theData, Additive.class);
                    log.info(pojo.toString());
                    Product product1 =
                        new Product(
                            "",
                            "",
                            "",
                            "",
                            "",
                            new ArrayList<>(Arrays.asList("")),
                            "",
                            "",
                            "",
                            "",
                            Arrays.asList(pojo),
                            new ArrayList<Intolerance>(
                                Arrays.asList(
                                    new Intolerance("", "", "", new ArrayList<String>()))),
                            "",
                            "");
                    /* CollectionReference additives = db.collection("PRODUCTS");
                    additives
                        .add(product1)
                        .addOnSuccessListener(
                            new OnSuccessListener<DocumentReference>() {
                              @Override
                              public void onSuccess(DocumentReference ref) {
                                additives
                                    .document(ref.getId())
                                    .update("productId", ref.getId());
                                product1.setProductId(ref.getId());
                              }
                            });*/
                  }
                }
              }
            });
    // for (int i = 0; i < 50; i++) {

    // }

    /*
    db = FirebaseFirestore.getInstance();
    Product product1 = new Product("", "Patatas fritas al punto de sal", "Patatas fritas al punto de sal LAY's", 207, "1", new ArrayList<String>(Arrays.asList("Lay's")), "900123123", "lays@lays.com", "Avenida de los olmos, 2 01013 - Vitoria, España", "Patatas, aceites vegetaless(maiz y girasol en proporciones variables), sal (1,2%)", new ArrayList<Additive>(Collections.singleton(new Additive("", "Curcumina", "E-100", 2, "Colorante"))), new ArrayList<Intolerance>(Collections.singleton(new Intolerance("", "Glúten", new ArrayList<String>(Arrays.asList("Patata", "Harina blanca", "Cereales"))))), "https://prod-mercadona.imgix.net/images/ff60554fe3825ea5e6b75c26b744b34c.jpg", "8410199021106");
    CollectionReference products = db.collection("PRODUCTS");
    products.document("TEST").set(product1).addOnSuccessListener(new OnSuccessListener() {
        @Override
        public void onSuccess(Object o) {
            Toast.makeText(getContext(), "Your Course has been added to Firebase Firestore",
            Toast.LENGTH_SHORT).show();
        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {
            // this method is called when the data addition process is failed.
            // displaying a toast message when data addition is failed.
            Toast.makeText(getContext(), "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
        }
    });
    db.collection("PRODUCTS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
        @Override
        public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    System.out.println(document.getData().get("image"));
                    final String urlimage = (String) document.getData()
                    .get("image");
                    System.out.println(urlimage);
                    Picasso.get().load(urlimage).into(product);
                }
            }
        }
    });*/
    return root;
  }

  private void showFavorites(View root) {
  }
}