package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

/**
 * Javadoc comment.
 */
public class ActivityProductView extends AppCompatActivity {

  private FirebaseFirestore db;
  private ImageView imageProduct;
  private TextView textGenericName;
  private TextView textQuantity;
  private TextView textInformationText;
  private TextView textFactoryAdress;
  private TextView textTelephone;

  private ImageView like;
  private ImageView dislike;
  private Boolean clicked = true;
  private Boolean clicked1 = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_productview);

    imageProduct = (ImageView) findViewById(R.id.imageView_product);
    textGenericName = (TextView) findViewById(R.id.textGenericName);
    textQuantity = (TextView) findViewById(R.id.textQuantity);
    textInformationText = (TextView) findViewById(R.id.textInformationText);
    textFactoryAdress = (TextView) findViewById(R.id.textFactoryAdress);
    textTelephone = (TextView) findViewById(R.id.textTelephone);

    like = (ImageView) findViewById(R.id.button_addFav);
    dislike = (ImageView) findViewById(R.id.button_addFav2);

    Product product = (Product) getIntent().getSerializableExtra("product");
    System.out.println("-------------------------------------------------" + product.getImage());

    Picasso.get().load(product.getImage()).into(imageProduct);
    textGenericName.setText(product.getGeneric_name());
    textQuantity.setText(product.getPackaging() + "" + product.getQuantity());
    textInformationText.setText(product.getInformation_text());
    textFactoryAdress.setText(product.getFactory_address());
    textTelephone.setText(product.getInformation_phone());
    
    like.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (clicked) {
          like.setImageResource(R.drawable.ic_like_selected);
          clicked = false;
          //TODO
        } else {
          clicked = true;
          like.setImageResource(R.drawable.ic_like_unselect);
        }
      }
    });

    dislike.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (clicked1) {
          dislike.setImageResource(R.drawable.ic_dislike_selected);
          clicked1 = false;
          //TODO
        } else {
          clicked1 = true;
          dislike.setImageResource(R.drawable.ic_dislike_unselected);
          //TODO
        }
      }
    });

    db = FirebaseFirestore.getInstance();
    db.collection("PRODUCTS").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
      @Override
      public void onComplete(@NonNull Task<QuerySnapshot> task) {
        if (task.isSuccessful()) {
          for (QueryDocumentSnapshot document : task.getResult()) {
            System.out.println(document.getData().get("image"));
            final String urlimage = (String) document.getData().get("image");
            System.out.println(urlimage);
            //Picasso.get().load(urlimage).into(imageProduct);
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