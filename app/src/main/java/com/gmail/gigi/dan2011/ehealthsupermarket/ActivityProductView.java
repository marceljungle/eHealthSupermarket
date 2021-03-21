package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.Map;

//import androidx.fragment.app.Fragment;

/**
 * Javadoc comment.
 */
public class ActivityProductView extends AppCompatActivity {

  private FirebaseFirestore db = FirebaseFirestore.getInstance();
  private FirebaseUser user;
  private ImageView imageProduct;
  private TextView textGenericName;
  private TextView textQuantity;
  private TextView textInformationText;
  private TextView textFactoryAdress;
  private TextView textTelephone;

  private ImageView like;
  private ImageView dislike;
  private LinearLayout layout_like;
  private LinearLayout layout_dislike;
  private Boolean clicked_like = false;
  private Boolean clicked_dilike = false;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_productview);

    // Get current user
    user = FirebaseAuth.getInstance().getCurrentUser();

    // Get the id of the views
    imageProduct = (ImageView) findViewById(R.id.imageView_product);
    textGenericName = (TextView) findViewById(R.id.textGenericName);
    textQuantity = (TextView) findViewById(R.id.textQuantity);
    textInformationText = (TextView) findViewById(R.id.textInformationText);
    textFactoryAdress = (TextView) findViewById(R.id.textFactoryAdress);
    textTelephone = (TextView) findViewById(R.id.textTelephone);
    like = (ImageView) findViewById(R.id.button_addFav);
    dislike = (ImageView) findViewById(R.id.button_addFav2);
    layout_like = (LinearLayout) findViewById(R.id.layout_like);
    layout_dislike = (LinearLayout) findViewById(R.id.layout_dislike);

    // Product clicked on the previous activity
    Product product = (Product) getIntent().getSerializableExtra("product");

    //Set the view of the product with the data obtained
    Picasso.get().load(product.getImage()).into(imageProduct);
    textGenericName.setText(product.getGeneric_name());
    textQuantity.setText(product.getPackaging() + "" + product.getQuantity());
    textInformationText.setText(product.getInformation_text());
    textFactoryAdress.setText(product.getFactory_address());
    textTelephone.setText("Número de atención: " + product.getInformation_phone());

    /**
     * Check if the product is in the liked products list
     */
    //TODO
    
    /**
     * Check if the product is in the liked products list
     */
    db.collection("USERS").document(user.getUid()).get().addOnCompleteListener(
        new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
              final ObjectMapper mapper = new ObjectMapper();
              Map<String, Object> document = task.getResult().getData();
              User actualUser = mapper.convertValue(document, User.class);
              if (actualUser.getLiked_products().contains(product)) {
                clicked_like = true;
                like.setImageResource(R.drawable.ic_like_selected);
                layout_like.setBackground(getDrawable(R.drawable.border_button_pressed));
              }
            }
          }
        });
    /**
     * Check if the product is in the disliked products list
     */
    db.collection("USERS").document(user.getUid()).get().addOnCompleteListener(
        new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
              final ObjectMapper mapper = new ObjectMapper();
              Map<String, Object> document = task.getResult().getData();
              User actualUser = mapper.convertValue(document, User.class);
              if (actualUser.getDisliked_products().contains(product)) {
                clicked_dilike = true;
                dislike.setImageResource(R.drawable.ic_dislike_selected);
                layout_dislike.setBackground(getDrawable(R.drawable.border_button_pressed));
              }
            }
          }
        });

    /**
     * Click on like Button
     */
    layout_like.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (clicked_like == false && clicked_dilike == false) {
          like.setImageResource(R.drawable.ic_like_selected);
          layout_like.setBackground(getDrawable(R.drawable.border_button_pressed));
          clicked_like = true;

          /**
           * Add product to liked products list
           */
          db.collection("USERS").document(user.getUid())
              .update("liked_products", FieldValue.arrayUnion(product));
          ///////////////////////////////////////////////////////

        } else if (clicked_like == true && clicked_dilike == false) {
          clicked_like = false;
          like.setImageResource(R.drawable.ic_like_unselect);
          layout_like.setBackground(getDrawable(R.drawable.border_button_unpressed));

          /**
           * Delete product from liked products list
           */
          db.collection("USERS").document(user.getUid())
              .update("liked_products", FieldValue.arrayRemove(product));


        } else if (clicked_like == false && clicked_dilike == true) {
          clicked_like = true;
          clicked_dilike = false;
          like.setImageResource(R.drawable.ic_like_selected);
          layout_like.setBackground(getDrawable(R.drawable.border_button_pressed));
          dislike.setImageResource(R.drawable.ic_dislike_unselected);
          layout_dislike.setBackground(getDrawable(R.drawable.border_button_unpressed));
          /**
           * Add product to liked products list
           */
          db.collection("USERS").document(user.getUid())
              .update("liked_products", FieldValue.arrayUnion(product));

          /**
           * Delete product from disliked products list
           */
          db.collection("USERS").document(user.getUid())
              .update("disliked_products", FieldValue.arrayRemove(product));

        }
      }
    });

    /**
     * Click on dislike Button
     */
    layout_dislike.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (clicked_dilike == false && clicked_like == false) {
          dislike.setImageResource(R.drawable.ic_dislike_selected);
          layout_dislike.setBackground(getDrawable(R.drawable.border_button_pressed));
          clicked_dilike = true;

          /**
           * Add product to disliked products list
           */
          db.collection("USERS").document(user.getUid())
              .update("disliked_products", FieldValue.arrayUnion(product));


        } else if (clicked_dilike == true && clicked_like == false) {
          clicked_dilike = false;
          dislike.setImageResource(R.drawable.ic_dislike_unselected);
          layout_dislike.setBackground(getDrawable(R.drawable.border_button_unpressed));

          /**
           * Delete product from disliked products list
           */
          db.collection("USERS").document(user.getUid())
              .update("disliked_products", FieldValue.arrayRemove(product));


        } else if (clicked_dilike == false && clicked_like == true) {
          clicked_dilike = true;
          clicked_like = false;
          dislike.setImageResource(R.drawable.ic_dislike_selected);
          layout_dislike.setBackground(getDrawable(R.drawable.border_button_pressed));
          like.setImageResource(R.drawable.ic_like_unselect);
          layout_like.setBackground(getDrawable(R.drawable.border_button_unpressed));
          /**
           * Add product to disliked products list
           */
          db.collection("USERS").document(user.getUid())
              .update("disliked_products", FieldValue.arrayUnion(product));
          /**
           * Delete product from liked products list
           */
          db.collection("USERS").document(user.getUid())
              .update("liked_products", FieldValue.arrayRemove(product));

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
