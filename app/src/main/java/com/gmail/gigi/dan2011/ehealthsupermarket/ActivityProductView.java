package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

/**
 * Javadoc comment.
 */
public class ActivityProductView extends AppCompatActivity {

  private FirebaseFirestore db;
  private ImageView imageProduct;
  private ImageView like;
  private ImageView dislike;
  private Boolean clicked = true;
  private Boolean clicked1 = true;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_productview);

    imageProduct = (ImageView) findViewById(R.id.imageView_product);

    like = (ImageView) findViewById(R.id.button_addFav);
    dislike = (ImageView) findViewById(R.id.button_addFav2);

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