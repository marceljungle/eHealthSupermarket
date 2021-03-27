package com.gmail.gigi.dan2011.ehealthsupermarket.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gigi.dan2011.ehealthsupermarket.ActivityProductView;
import com.gmail.gigi.dan2011.ehealthsupermarket.ActivityProductsListView;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
  private FirebaseFirestore db = FirebaseFirestore.getInstance();
  private FirebaseUser user;
  private Button viewMore1;
  private Logger log = LoggerFactory.getLogger(HomeFragment.class);
  private LinearLayout[] favorites = new LinearLayout[5];
  private LinearLayout[] basedInIntolerances = new LinearLayout[5];
  private ImageButton[] favImageButtons = new ImageButton[5];
  private ImageButton[] basedInIntolerancesImageButtons = new ImageButton[5];
  private TextView[] favNames = new TextView[5];
  private TextView[] basedInIntolerancesNames = new TextView[5];
  private TextView[] favQuantity = new TextView[5];
  private TextView[] basedInIntolerancesQuantity = new TextView[5];
  private LinearLayout noFavorites;
  private LinearLayout noBasedInIntolerances;

  /**
   * Represents an example of javadoc in a function.
   */
  public View onCreateView(
      @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    // Get Auth Firebase reference
    mauth = FirebaseAuth.getInstance();
    // Get current user
    user = FirebaseAuth.getInstance().getCurrentUser();

    homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    View root = inflater.inflate(R.layout.fragment_home, container, false);

    /*  Button to view the activity that contains the product information.*/
    ImageButton product = (ImageButton) root.findViewById(R.id.imageFav1);
    ImageView imageProduct = (ImageView) root.findViewById(R.id.imageView_product);

    /*Button to view the activity the contains liked products*/
    Button viewMore = (Button) root.findViewById(R.id.button_viewMore);
    viewMore.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ActivityProductsListView.class);
        intent.putExtra("show", "likedProducts");
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
/*
    //AÑADE UN PRODUCTO ESPECÍFICO A LOS FAVORITOS DEL USUARIO RESCATANDO PREVIAMENTE EL PRODUCTO
    db.collection("PRODUCTS").document("01ir2JNAshVPjH6GGafd")
        .get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            final ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> document = task.getResult().getData();
            Product pojo = mapper.convertValue(document, Product.class);
            System.out.println(pojo.toString());
            log.info(pojo.toString());
            db.collection("USERS").document(user.getUid())
                .update("liked_products", Arrays.asList(pojo, pojo));

            System.out.println(pojo.toString());
          }
        });
*/


    /*
    AÑADE UN PRODUCTO A LA COLECCION PRODUCTS CON UN IDENTIFICADOR ESPECÍFICO
    db = FirebaseFirestore.getInstance();
      Product productPrueba = new Product("", "Patatas fritas al punto de sal",
              "Patatas fritas al punto de sal LAY's", "207", "1",
              new ArrayList<String>(Arrays.asList("Lay's")), "900123123", "lays@lays.com",
              "Avenida de los olmos, 2 01013 - Vitoria, España",
              "Patatas, aceites vegetaless(maiz y girasol en proporciones variables), sal (1,2%)",
              new ArrayList<Additive>(
                  Collections.singleton(new Additive("", "Curcumina", "E-100", 2, "Colorante"))),
              Arrays.asList(new Intolerance("2", "Glúten", "", Arrays.asList("asd", "dasd"))),
              "https://prod-mercadona.imgix.net/images/ff60554fe3825ea5e6b75c26b744b34c.jpg",
              "8410199021106");
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

    //EXTRAE LA URL DE LA IMAGEN DE UN PRODUCTO
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

    favorites[0] = root.findViewById(R.id.favorite1);
    favorites[1] = root.findViewById(R.id.favorite2);
    favorites[2] = root.findViewById(R.id.favorite3);
    favorites[3] = root.findViewById(R.id.favorite4);
    favorites[4] = root.findViewById(R.id.favorite5);
    basedInIntolerances[0] = root.findViewById(R.id.intBased1);
    basedInIntolerances[1] = root.findViewById(R.id.intBased2);
    basedInIntolerances[2] = root.findViewById(R.id.intBased3);
    basedInIntolerances[3] = root.findViewById(R.id.intBased4);
    basedInIntolerances[4] = root.findViewById(R.id.intBased5);
    noFavorites = root.findViewById(R.id.noFavoriteProducts);
    noBasedInIntolerances = root.findViewById(R.id.noBasedInIntoleranceProducts);

    showFavorites(root);
    // showBasedInIntolerances(root);
    return root;
  }

  private void showFavorites(View root) {
    String userUid = user.getUid();
    final ObjectMapper mapper = new ObjectMapper();
    db.collection("USERS").document(userUid).get().addOnCompleteListener(
        new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            List<Product> likedProducts = new ArrayList<>();

            List<Map<String, Object>> favProducts = new ArrayList<>();
            try {
              favProducts = (List) task.getResult()
                  .getData().get("liked_products");
              log.info("Obtained user liked products from Firebase!");
            } catch (Exception e) {
              log.error("User doesn't have liked products! User wrongly created..?");
            }
            for (Map<String, Object> mapProd : favProducts) {
              likedProducts.add(mapper.convertValue(mapProd, Product.class));
            }
            showFavorites2(root, likedProducts);
          }
        });
  }

  private void showFavorites2(View root, List<Product> likedProducts) {

    Integer blankLayouts = 5 - likedProducts.size();
    Integer filledLayouts = likedProducts.size();
    Collections.shuffle(likedProducts);
    if (!likedProducts.isEmpty()) {
      Intent in = new Intent(getActivity(), ActivityProductView.class);
      for (int i = 0; i < filledLayouts; i++) {
        switch (i) {
          case 0:
            favorites[0].setVisibility(View.VISIBLE);
            favImageButtons[0] = root.findViewById(R.id.imageFav1);
            favNames[0] = root.findViewById(R.id.nameFav1);
            favQuantity[0] = root.findViewById(R.id.quantityFav1);
            Picasso.get().load(likedProducts.get(0).getImage()).into(favImageButtons[0]);
            favNames[0].setText(likedProducts.get(0).getGeneric_name());
            favQuantity[0].setText(
                likedProducts.get(0).getPackaging() + " " + likedProducts.get(0).getQuantity());
            favorites[0].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                in.putExtra("product", likedProducts.get(0));
                startActivity(in);
              }
            });
            break;
          case 1:
            favorites[1].setVisibility(View.VISIBLE);
            favImageButtons[1] = root.findViewById(R.id.imageFav2);
            favNames[1] = root.findViewById(R.id.nameFav2);
            favQuantity[1] = root.findViewById(R.id.quantityFav2);
            Picasso.get().load(likedProducts.get(1).getImage()).into(favImageButtons[1]);
            favNames[1].setText(likedProducts.get(1).getGeneric_name());
            favQuantity[1].setText(
                likedProducts.get(1).getPackaging() + " " + likedProducts.get(1).getQuantity());
            favorites[1].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                in.putExtra("product", likedProducts.get(1));
                startActivity(in);
              }
            });
            break;
          case 2:
            favorites[2].setVisibility(View.VISIBLE);
            favImageButtons[2] = root.findViewById(R.id.imageFav3);
            favNames[2] = root.findViewById(R.id.nameFav3);
            favQuantity[2] = root.findViewById(R.id.quantityFav3);
            Picasso.get().load(likedProducts.get(2).getImage()).into(favImageButtons[2]);
            favNames[2].setText(likedProducts.get(2).getGeneric_name());
            favQuantity[2].setText(
                likedProducts.get(2).getPackaging() + " " + likedProducts.get(2).getQuantity());
            favorites[2].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                in.putExtra("product", likedProducts.get(2));
                startActivity(in);
              }
            });
            break;
          case 3:
            favorites[3].setVisibility(View.VISIBLE);
            favImageButtons[3] = root.findViewById(R.id.imageFav4);
            favNames[3] = root.findViewById(R.id.nameFav4);
            favQuantity[3] = root.findViewById(R.id.quantityFav4);
            Picasso.get().load(likedProducts.get(3).getImage()).into(favImageButtons[3]);
            favNames[3].setText(likedProducts.get(3).getGeneric_name());
            favQuantity[3].setText(
                likedProducts.get(3).getPackaging() + " " + likedProducts.get(3).getQuantity());
            favorites[3].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                in.putExtra("product", likedProducts.get(3));
                startActivity(in);
              }
            });
            break;
          case 4:
            favorites[4].setVisibility(View.VISIBLE);
            favImageButtons[4] = root.findViewById(R.id.imageFav5);
            favNames[4] = root.findViewById(R.id.nameFav5);
            favQuantity[4] = root.findViewById(R.id.quantityFav5);
            Picasso.get().load(likedProducts.get(4).getImage()).into(favImageButtons[4]);
            favNames[4].setText(likedProducts.get(4).getGeneric_name());
            favQuantity[4].setText(
                likedProducts.get(4).getPackaging() + " " + likedProducts.get(4).getQuantity());
            favorites[4].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                in.putExtra("product", likedProducts.get(4));
                startActivity(in);
              }
            });

            break;
          default:
            favorites[0].setVisibility(View.GONE);
            favorites[1].setVisibility(View.GONE);
            favorites[2].setVisibility(View.GONE);
            favorites[3].setVisibility(View.GONE);
            favorites[4].setVisibility(View.GONE);
            noFavorites.setVisibility(View.VISIBLE);

            break;
        }
      }
      for (int i = filledLayouts; i < 5; i++) {
        favorites[i].setVisibility(View.GONE);
      }
    } else {
      favorites[0].setVisibility(View.GONE);
      favorites[1].setVisibility(View.GONE);
      favorites[2].setVisibility(View.GONE);
      favorites[3].setVisibility(View.GONE);
      favorites[4].setVisibility(View.GONE);
      noFavorites.setVisibility(View.VISIBLE);
    }
  }

  private void showBasedInIntolerances(View root) {
    String userUid = user.getUid();
    final ObjectMapper mapper = new ObjectMapper();

    /* TODO: retrieve products from database and apply an algorithm to get
        recomended products based in user intolerances */
  }

  private void showBasedInIntolerances2(View root, List<Product> basedIntoIntolerancesProducts) {

    Integer blankLayouts = 5 - basedIntoIntolerancesProducts.size();
    Integer filledLayouts = basedIntoIntolerancesProducts.size();
    Collections.shuffle(basedIntoIntolerancesProducts);
    if (!basedIntoIntolerancesProducts.isEmpty()) {
      for (int i = 0; i < filledLayouts; i++) {
        switch (i) {
          case 0:
            basedInIntolerances[0].setVisibility(View.VISIBLE);
            basedInIntolerancesImageButtons[0] = root.findViewById(R.id.imgIntBased1);
            basedInIntolerancesNames[0] = root.findViewById(R.id.nameIntBased1);
            basedInIntolerancesQuantity[0] = root.findViewById(R.id.quantityIntBased1);
            Picasso.get().load(basedIntoIntolerancesProducts.get(0).getImage())
                .into(basedInIntolerancesImageButtons[0]);
            basedInIntolerancesNames[0]
                .setText(basedIntoIntolerancesProducts.get(0).getGeneric_name());
            basedInIntolerancesQuantity[0].setText(
                basedIntoIntolerancesProducts.get(0).getPackaging() + " "
                    + basedIntoIntolerancesProducts.get(0).getQuantity());
            break;
          case 1:
            basedInIntolerances[1].setVisibility(View.VISIBLE);
            basedInIntolerancesImageButtons[1] = root.findViewById(R.id.imgIntBased2);
            basedInIntolerancesNames[1] = root.findViewById(R.id.nameIntBased2);
            basedInIntolerancesQuantity[1] = root.findViewById(R.id.quantityIntBased2);
            Picasso.get().load(basedIntoIntolerancesProducts.get(1).getImage())
                .into(basedInIntolerancesImageButtons[1]);
            basedInIntolerancesNames[1]
                .setText(basedIntoIntolerancesProducts.get(1).getGeneric_name());
            basedInIntolerancesQuantity[1].setText(
                basedIntoIntolerancesProducts.get(1).getPackaging() + " "
                    + basedIntoIntolerancesProducts.get(1).getQuantity());
            break;
          case 2:
            basedInIntolerances[2].setVisibility(View.VISIBLE);
            basedInIntolerancesImageButtons[2] = root.findViewById(R.id.imgIntBased3);
            basedInIntolerancesNames[2] = root.findViewById(R.id.nameIntBased3);
            basedInIntolerancesQuantity[2] = root.findViewById(R.id.quantityIntBased3);
            Picasso.get().load(basedIntoIntolerancesProducts.get(2).getImage())
                .into(basedInIntolerancesImageButtons[2]);
            basedInIntolerancesNames[2]
                .setText(basedIntoIntolerancesProducts.get(2).getGeneric_name());
            basedInIntolerancesQuantity[2].setText(
                basedIntoIntolerancesProducts.get(2).getPackaging() + " "
                    + basedIntoIntolerancesProducts.get(2).getQuantity());
            break;
          case 3:
            basedInIntolerances[3].setVisibility(View.VISIBLE);
            basedInIntolerancesImageButtons[3] = root.findViewById(R.id.imgIntBased4);
            basedInIntolerancesNames[3] = root.findViewById(R.id.nameIntBased4);
            basedInIntolerancesQuantity[3] = root.findViewById(R.id.quantityIntBased4);
            Picasso.get().load(basedIntoIntolerancesProducts.get(3).getImage())
                .into(basedInIntolerancesImageButtons[3]);
            basedInIntolerancesNames[3]
                .setText(basedIntoIntolerancesProducts.get(3).getGeneric_name());
            basedInIntolerancesQuantity[3].setText(
                basedIntoIntolerancesProducts.get(3).getPackaging() + " "
                    + basedIntoIntolerancesProducts.get(3).getQuantity());
            break;
          case 4:
            basedInIntolerances[4].setVisibility(View.VISIBLE);
            basedInIntolerancesImageButtons[4] = root.findViewById(R.id.imgIntBased5);
            basedInIntolerancesNames[4] = root.findViewById(R.id.nameIntBased5);
            basedInIntolerancesQuantity[4] = root.findViewById(R.id.quantityIntBased5);
            Picasso.get().load(basedIntoIntolerancesProducts.get(4).getImage())
                .into(basedInIntolerancesImageButtons[4]);
            basedInIntolerancesNames[4]
                .setText(basedIntoIntolerancesProducts.get(4).getGeneric_name());
            basedInIntolerancesQuantity[4].setText(
                basedIntoIntolerancesProducts.get(4).getPackaging() + " "
                    + basedIntoIntolerancesProducts.get(4).getQuantity());
            break;
          default:
            favorites[0].setVisibility(View.GONE);
            favorites[1].setVisibility(View.GONE);
            favorites[2].setVisibility(View.GONE);
            favorites[3].setVisibility(View.GONE);
            favorites[4].setVisibility(View.GONE);
            noFavorites.setVisibility(View.VISIBLE);
            break;
        }
      }
      for (int i = filledLayouts; i < 5; i++) {
        basedInIntolerances[i].setVisibility(View.GONE);
      }
    } else {
      basedInIntolerances[0].setVisibility(View.GONE);
      basedInIntolerances[1].setVisibility(View.GONE);
      basedInIntolerances[2].setVisibility(View.GONE);
      basedInIntolerances[3].setVisibility(View.GONE);
      basedInIntolerances[4].setVisibility(View.GONE);
      noBasedInIntolerances.setVisibility(View.VISIBLE);
    }
  }
}