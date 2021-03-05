package com.gmail.gigi.dan2011.ehealthsupermarket.ui.home;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.gmail.gigi.dan2011.ehealthsupermarket.activity_productview;
import com.gmail.gigi.dan2011.ehealthsupermarket.dbCollections.Additive;
import com.gmail.gigi.dan2011.ehealthsupermarket.dbCollections.Ingredient;
import com.gmail.gigi.dan2011.ehealthsupermarket.dbCollections.Intolerance;
import com.gmail.gigi.dan2011.ehealthsupermarket.dbCollections.Product;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

public class homeFragment extends Fragment {

    private homeViewModel homeViewModel;
    private FirebaseAuth mauth;
    private FirebaseFirestore db;

    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        //Get Auth Firebase reference
        mauth = FirebaseAuth.getInstance();
        //Get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        homeViewModel = new ViewModelProvider(this).get(homeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        /*  Button to view the activity that contains the product information.*/
        ImageButton product = (ImageButton) root.findViewById(R.id.imagebuttonproduct2);
        product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getActivity(), activity_productview.class);
                startActivity(in);
            }
        });

        /* TEST */
        db = FirebaseFirestore.getInstance();
        Product product1 = new Product("", "Patatas fritas al punto de sal", "Patatas fritas al punto de sal LAY's", 207, "1", new ArrayList<String>(Arrays.asList("Lay's")), "900123123", "lays@lays.com", "Avenida de los olmos, 2 01013 - Vitoria, España", "Patatas, aceites vegetaless(maiz y girasol en proporciones variables), sal (1,2%)", new ArrayList<Ingredient>(Collections.singleton(new Ingredient("", "Patatas"))), new ArrayList<Additive>(Collections.singleton(new Additive("", "Curcumina", "E-100", 2, "Colorante"))), new ArrayList<Intolerance>(Collections.singleton(new Intolerance("", "Glúten", new ArrayList<String>(Arrays.asList("Patata", "Harina blanca", "Cereales"))))), "https://prod-mercadona.imgix.net/images/ff60554fe3825ea5e6b75c26b744b34c.jpg", "8410199021106");
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
        ImageButton imageButton = root.findViewById(R.id.imagebuttonproduct2);

        /*        product1.setProduct_id();*/

        db.collection("PRODUCTS").document("TEST").get();

        imageButton.setBackground(LoadImageFromWebOperations());


        return root;
    }


}
