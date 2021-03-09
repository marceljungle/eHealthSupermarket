package com.gmail.gigi.dan2011.ehealthsupermarket.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.gmail.gigi.dan2011.ehealthsupermarket.activity_productview;
import com.gmail.gigi.dan2011.ehealthsupermarket.dbCollections.Additive;
import com.gmail.gigi.dan2011.ehealthsupermarket.dbCollections.Intolerance;
import com.gmail.gigi.dan2011.ehealthsupermarket.dbCollections.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class homeFragment extends Fragment {

    private homeViewModel homeViewModel;
    private ImageView imageProduct;
    private FirebaseAuth mauth;
    private FirebaseFirestore db;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //Get Auth Firebase reference
        mauth = FirebaseAuth.getInstance();
        //Get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        homeViewModel = new ViewModelProvider(this).get(homeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        /*  Button to view the activity that contains the product information.*/
        ImageButton product = (ImageButton) root.findViewById(R.id.imagebuttonproduct2);
        ImageView imageProduct = (ImageView) root.findViewById(R.id.imageView_product);

        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), activity_productview.class);
                startActivity(in);
            }
        });

        db = FirebaseFirestore.getInstance();
        db.collection("ADDITIVES").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    final ObjectMapper mapper = new ObjectMapper();
                    for (int i = 0; i < 50; i++) {
                        DocumentSnapshot document = task.getResult().getDocuments().get(0);
                        Map<String, Object> theData = document.getData();
                        Additive pojo = mapper.convertValue(theData, Additive.class);

                        Product product1 = new Product("", "", "", "", "", new ArrayList<>(Arrays.asList("")), "", "", "", "", Arrays.asList(pojo), new ArrayList<Intolerance>(Arrays.asList(new Intolerance("", "", "", new ArrayList<String>()))), "", "");
                        CollectionReference additives = db.collection("PRODUCTS");
                        additives.add(product1).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference aVoid) {
                                additives.document(aVoid.getId()).update("product_id", aVoid.getId());
                                product1.setProduct_id(aVoid.getId());
                            }
                        });
                    }
                }
            }
        });
        // for (int i = 0; i < 50; i++) {

        //}

        /*
        db = FirebaseFirestore.getInstance();
        Product product1 = new Product("", "Patatas fritas al punto de sal", "Patatas fritas al punto de sal LAY's", 207, "1", new ArrayList<String>(Arrays.asList("Lay's")), "900123123", "lays@lays.com", "Avenida de los olmos, 2 01013 - Vitoria, España", "Patatas, aceites vegetaless(maiz y girasol en proporciones variables), sal (1,2%)", new ArrayList<Additive>(Collections.singleton(new Additive("", "Curcumina", "E-100", 2, "Colorante"))), new ArrayList<Intolerance>(Collections.singleton(new Intolerance("", "Glúten", new ArrayList<String>(Arrays.asList("Patata", "Harina blanca", "Cereales"))))), "https://prod-mercadona.imgix.net/images/ff60554fe3825ea5e6b75c26b744b34c.jpg", "8410199021106");
        CollectionReference products = db.collection("PRODUCTS");
        products.document("TEST").set(product1).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                Toast.makeText(getContext(), "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
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
                        final String urlimage = (String) document.getData().get("image");
                        System.out.println("/n-----------------------------------------22222---------------------------------------------------------");
                        System.out.println(urlimage);
                        //Picasso.get().load(urlimage).into(product);
                    }
                }
            }
        });*/
        return root;
    }

    private void showFavorites(View root) {

    }

}
