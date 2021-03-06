package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class activity_productview extends AppCompatActivity {
    private FirebaseFirestore db;
    private ImageView imageProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productview);

        imageProduct = (ImageView) findViewById(R.id.imageView_product);

        db = FirebaseFirestore.getInstance();
        db.collection("PRODUCTS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        System.out.println("/n--------------------------------------------------------------------------------------------------");
                        System.out.println(document.getData().get("image"));
                        final String urlimage = (String) document.getData().get("image");
                        System.out.println("/n-----------------------------------------22222---------------------------------------------------------");
                        System.out.println(urlimage);
                        Picasso.get().load(urlimage).into(imageProduct);
                    }
                }
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println(item.getItemId());
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return true;
    }
}