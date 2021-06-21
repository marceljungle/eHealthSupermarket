package com.gmail.gigi.dan2011.ehealthsupermarket.sections.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gigi.dan2011.ehealthsupermarket.sections.product.ActivityProductView;
import com.gmail.gigi.dan2011.ehealthsupermarket.sections.product.ActivityProductsListView;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Additive;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Featured;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Intolerance;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
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
  private LinearLayout[] featuredLayouts = new LinearLayout[2];
  private ImageView[] favImageButtons = new ImageView[5];
  private ImageView[] basedInIntolerancesImageButtons = new ImageView[5];
  private ImageView[] featured_images = new ImageView[2];
  private TextView[] favNames = new TextView[5];
  private TextView[] basedInIntolerancesNames = new TextView[5];
  private TextView[] favQuantity = new TextView[5];
  private TextView[] basedInIntolerancesQuantity = new TextView[5];
  private TextView[] featured_titles = new TextView[2];
  private TextView[] featured_subtitles = new TextView[2];
  private LinearLayout noFavorites;
  private LinearLayout noBasedInIntolerances;
  private View rootView;
  private Button goToBasedInIntolerances;
  private Button goToScanProducts;

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
    featured_images[0] = root.findViewById(R.id.image_rectangle1);
    featured_images[1] = root.findViewById(R.id.image_rectangle2);
    featured_titles[0] = root.findViewById(R.id.title_rectangle1);
    featured_titles[1] = root.findViewById(R.id.title_rectangle2);
    featured_subtitles[0] = root.findViewById(R.id.subtitle_rectangle1);
    featured_subtitles[1] = root.findViewById(R.id.subtitle_rectangle2);
    featuredLayouts[0] = root.findViewById(R.id.rectangle1);
    featuredLayouts[1] = root.findViewById(R.id.rectangle2);



    goToBasedInIntolerances = root.findViewById(R.id.goToIntolerancesButton);
    goToBasedInIntolerances.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_home_fragment_to_intolerances);
      }
    });

    goToScanProducts = root.findViewById(R.id.goToScanButton);
    goToScanProducts.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Navigation.findNavController(v).navigate(R.id.action_home_fragment_to_scan);
      }
    });


    showFavorites(root);
    showBasedInIntolerances(root);
    showFeatured(root);
    rootView = root;
    return root;
  }

  @Override
  public void onResume() {
    super.onResume();
    showFavorites(rootView);
    showBasedInIntolerances(rootView);
    showFeatured(rootView);
  }

  private void showFavorites(View root) {

    String userUid = user.getUid();
    final ObjectMapper mapper = new ObjectMapper();
    db.collection("USERS").document(userUid).get().addOnCompleteListener(
        new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            Runnable runnable = new Runnable() {
              @Override
              public void run() {
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

                root.post(new Runnable() {
                  @Override
                  public void run() {
                    showFavorites2(root, likedProducts);
                  }
                });

              }
            };
            new Thread(runnable).start();
          }
        });
  }

  private void showFavorites2(View root, List<Product> likedProducts) {

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

    Integer filledLayouts = likedProducts.size();
    if (filledLayouts > 5) {
      filledLayouts = 5;
    }
    Collections.shuffle(likedProducts);
    if (!likedProducts.isEmpty()) {
      for (int i = 0; i < filledLayouts; i++) {
        switch (i) {
          case 0:
            favorites[0].setVisibility(View.VISIBLE);
            favImageButtons[0] = root.findViewById(R.id.imageFav1);
            favNames[0] = root.findViewById(R.id.nameFav1);
            favQuantity[0] = root.findViewById(R.id.quantityFav1);
            Picasso.get().load(likedProducts.get(0).getImage()).fit().centerCrop()
                .into(favImageButtons[0]);
            favNames[0].setText(likedProducts.get(0).getGeneric_name());
            favQuantity[0].setText(
                likedProducts.get(0).getPackaging() + " " + likedProducts.get(0).getQuantity());
            favorites[0].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent in = new Intent(getActivity(), ActivityProductView.class);
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
            Picasso.get().load(likedProducts.get(1).getImage()).fit().centerCrop()
                .into(favImageButtons[1]);
            favNames[1].setText(likedProducts.get(1).getGeneric_name());
            favQuantity[1].setText(
                likedProducts.get(1).getPackaging() + " " + likedProducts.get(1).getQuantity());
            favorites[1].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent in = new Intent(getActivity(), ActivityProductView.class);
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
            Picasso.get().load(likedProducts.get(2).getImage()).fit().centerCrop()
                .into(favImageButtons[2]);
            favNames[2].setText(likedProducts.get(2).getGeneric_name());
            favQuantity[2].setText(
                likedProducts.get(2).getPackaging() + " " + likedProducts.get(2).getQuantity());
            favorites[2].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent in = new Intent(getActivity(), ActivityProductView.class);
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
            Picasso.get().load(likedProducts.get(3).getImage()).fit().centerCrop()
                .into(favImageButtons[3]);
            favNames[3].setText(likedProducts.get(3).getGeneric_name());
            favQuantity[3].setText(
                likedProducts.get(3).getPackaging() + " " + likedProducts.get(3).getQuantity());
            favorites[3].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent in = new Intent(getActivity(), ActivityProductView.class);
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
            Picasso.get().load(likedProducts.get(4).getImage()).fit().centerCrop()
                .into(favImageButtons[4]);
            favNames[4].setText(likedProducts.get(4).getGeneric_name());
            favQuantity[4].setText(
                likedProducts.get(4).getPackaging() + " " + likedProducts.get(4).getQuantity());
            favorites[4].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent in = new Intent(getActivity(), ActivityProductView.class);
                in.putExtra("product", likedProducts.get(4));
                startActivity(in);
              }
            });

            break;
          default:
            favorites[0].setVisibility(View.INVISIBLE);
            favorites[1].setVisibility(View.GONE);
            favorites[2].setVisibility(View.GONE);
            favorites[3].setVisibility(View.GONE);
            favorites[4].setVisibility(View.GONE);
            viewMore.setVisibility(View.GONE);
            noFavorites.setVisibility(View.VISIBLE);
            break;
        }
      }
      for (int i = filledLayouts; i < 5; i++) {
        favorites[i].setVisibility(View.GONE);
      }
    } else {
      favorites[0].setVisibility(View.INVISIBLE);
      favorites[1].setVisibility(View.GONE);
      favorites[2].setVisibility(View.GONE);
      favorites[3].setVisibility(View.GONE);
      favorites[4].setVisibility(View.GONE);
      viewMore.setVisibility(View.GONE);
      noFavorites.setVisibility(View.VISIBLE);
    }
  }


  private void showBasedInIntolerances(View root) {

    String userUid = user.getUid();
    final ObjectMapper mapper = new ObjectMapper();

    // check user intolerances
    db.collection("USERS").document(userUid).get().addOnCompleteListener(
        new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            List<Intolerance> intolerances = new ArrayList<>();
            List<Additive> additives = new ArrayList<>();

            List<Product> allProducts = new ArrayList<>();
            List<Additive> allAdditives = new ArrayList<>();

            List<Map<String, Object>> intoleranceList = new ArrayList<>();
            List<Map<String, Object>> additiveList = new ArrayList<>();
            try {
              intoleranceList = (List) task.getResult()
                  .getData().get("intolerances");

              additiveList = (List) task.getResult()
                  .getData().get("unsupported_additives");
              log.info("Obtained user info from Firebase!");
            } catch (Exception e) {
              log.error(
                  "User doesn't have info of products! User wrongly created..?");
            }

            for (Map<String, Object> mapIntolerance : intoleranceList) {
              intolerances.add(mapper.convertValue(mapIntolerance, Intolerance.class));
            }
            for (Map<String, Object> mapAdditive : additiveList) {
              additives.add(mapper.convertValue(mapAdditive, Additive.class));
            }

            // let's get all products
            db.collection("PRODUCTS").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    Map<String, Object> objectMap = new HashMap<>();
                    Product product = new Product();
                    if (task.isSuccessful()) {
                      final ObjectMapper mapper = new ObjectMapper();
                      for (QueryDocumentSnapshot document : task.getResult()) {
                        try {
                          objectMap = document.getData();
                        } catch (IllegalArgumentException e) {
                          log.debug(
                              "Failed to get product, maybe is empty or it have the 'id' attribute");
                        }
                        if (!objectMap.containsKey("id") && !objectMap
                            .containsKey("ingredients")) {
                          product = mapper.convertValue(objectMap, Product.class);
                        }
                        if (product.getProduct_name() != null
                            && product.getProduct_name() != "") {
                          allProducts.add(product);
                        }
                      }
                    }

                    List<Product> whiteListedProducts = new ArrayList<>();
                    boolean toAdd = false;
                    for (Product p : allProducts) {
                      if (p.getIntolerances() == null || intolerances.isEmpty() || Collections
                          .disjoint(p.getIntolerances(), intolerances)) {
                        toAdd = true;
                      }else {
                        toAdd = false;
                      }

                      if (p.getAdditives() == null || additives.isEmpty() || Collections
                          .disjoint(p.getAdditives(), additives)) {
                        toAdd = toAdd && true;

                      }else {
                        toAdd = false;
                      }

                      if (toAdd) {
                        whiteListedProducts.add(p);
                      } else {
                        continue;
                      }
                    }

                    List<Product> whiteListedProducts1 = new ArrayList<>(
                        new HashSet<>(whiteListedProducts));
                    showBasedInIntolerances2(root, whiteListedProducts1);


                  }
                });
          }
        });
  }

  private void showBasedInIntolerances2(View root,
      List<Product> basedIntoIntolerancesProducts) {

    /*Button to view the activity the contains basedIntoInIntolerances products*/
    Button viewMore1 = (Button) root.findViewById(R.id.button_viewMore1);
    viewMore1.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), ActivityProductsListView.class);
        Bundle args = new Bundle();
        args.putSerializable("show", (Serializable) "basedInIntolerances");
        args.putSerializable("ARRAYLIST", (Serializable) basedIntoIntolerancesProducts);
        intent.putExtra("basedInIntolerances", args);
        startActivity(intent);
      }
    });

    Integer filledLayouts = basedIntoIntolerancesProducts.size();
    if (filledLayouts > 5) {
      filledLayouts = 5;
    }
    Collections.shuffle(basedIntoIntolerancesProducts);
    if (!basedIntoIntolerancesProducts.isEmpty()) {
      for (int i = 0; i < filledLayouts; i++) {
        switch (i) {
          case 0:
            basedInIntolerances[0].setVisibility(View.VISIBLE);
            basedInIntolerancesImageButtons[0] = root.findViewById(R.id.imgIntBased1);
            basedInIntolerancesNames[0] = root.findViewById(R.id.nameIntBased1);
            basedInIntolerancesQuantity[0] = root.findViewById(R.id.quantityIntBased1);
            Picasso.get().load(basedIntoIntolerancesProducts.get(0).getImage()).fit().centerCrop()
                .into(basedInIntolerancesImageButtons[0]);
            basedInIntolerancesNames[0]
                .setText(basedIntoIntolerancesProducts.get(0).getGeneric_name());
            basedInIntolerancesQuantity[0].setText(
                basedIntoIntolerancesProducts.get(0).getPackaging() + " "
                    + basedIntoIntolerancesProducts.get(0).getQuantity());
            basedInIntolerances[0].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent in = new Intent(getActivity(), ActivityProductView.class);
                in.putExtra("product", basedIntoIntolerancesProducts.get(0));
                startActivity(in);
              }
            });
            break;
          case 1:
            basedInIntolerances[1].setVisibility(View.VISIBLE);
            basedInIntolerancesImageButtons[1] = root.findViewById(R.id.imgIntBased2);
            basedInIntolerancesNames[1] = root.findViewById(R.id.nameIntBased2);
            basedInIntolerancesQuantity[1] = root.findViewById(R.id.quantityIntBased2);
            Picasso.get().load(basedIntoIntolerancesProducts.get(1).getImage()).fit().centerCrop()
                .into(basedInIntolerancesImageButtons[1]);
            basedInIntolerancesNames[1]
                .setText(basedIntoIntolerancesProducts.get(1).getGeneric_name());
            basedInIntolerancesQuantity[1].setText(
                basedIntoIntolerancesProducts.get(1).getPackaging() + " "
                    + basedIntoIntolerancesProducts.get(1).getQuantity());
            basedInIntolerances[1].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent in = new Intent(getActivity(), ActivityProductView.class);
                in.putExtra("product", basedIntoIntolerancesProducts.get(1));
                startActivity(in);
              }
            });
            break;
          case 2:
            basedInIntolerances[2].setVisibility(View.VISIBLE);
            basedInIntolerancesImageButtons[2] = root.findViewById(R.id.imgIntBased3);
            basedInIntolerancesNames[2] = root.findViewById(R.id.nameIntBased3);
            basedInIntolerancesQuantity[2] = root.findViewById(R.id.quantityIntBased3);
            Picasso.get().load(basedIntoIntolerancesProducts.get(2).getImage()).fit().centerCrop()
                .into(basedInIntolerancesImageButtons[2]);
            basedInIntolerancesNames[2]
                .setText(basedIntoIntolerancesProducts.get(2).getGeneric_name());
            basedInIntolerancesQuantity[2].setText(
                basedIntoIntolerancesProducts.get(2).getPackaging() + " "
                    + basedIntoIntolerancesProducts.get(2).getQuantity());
            basedInIntolerances[2].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent in = new Intent(getActivity(), ActivityProductView.class);
                in.putExtra("product", basedIntoIntolerancesProducts.get(2));
                startActivity(in);
              }
            });
            break;
          case 3:
            basedInIntolerances[3].setVisibility(View.VISIBLE);
            basedInIntolerancesImageButtons[3] = root.findViewById(R.id.imgIntBased4);
            basedInIntolerancesNames[3] = root.findViewById(R.id.nameIntBased4);
            basedInIntolerancesQuantity[3] = root.findViewById(R.id.quantityIntBased4);
            Picasso.get().load(basedIntoIntolerancesProducts.get(3).getImage()).fit().centerCrop()
                .into(basedInIntolerancesImageButtons[3]);
            basedInIntolerancesNames[3]
                .setText(basedIntoIntolerancesProducts.get(3).getGeneric_name());
            basedInIntolerancesQuantity[3].setText(
                basedIntoIntolerancesProducts.get(3).getPackaging() + " "
                    + basedIntoIntolerancesProducts.get(3).getQuantity());
            basedInIntolerances[3].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent in = new Intent(getActivity(), ActivityProductView.class);
                in.putExtra("product", basedIntoIntolerancesProducts.get(3));
                startActivity(in);
              }
            });
            break;
          case 4:
            basedInIntolerances[4].setVisibility(View.VISIBLE);
            basedInIntolerancesImageButtons[4] = root.findViewById(R.id.imgIntBased5);
            basedInIntolerancesNames[4] = root.findViewById(R.id.nameIntBased5);
            basedInIntolerancesQuantity[4] = root.findViewById(R.id.quantityIntBased5);
            Picasso.get().load(basedIntoIntolerancesProducts.get(4).getImage()).fit().centerCrop()
                .into(basedInIntolerancesImageButtons[4]);
            basedInIntolerancesNames[4]
                .setText(basedIntoIntolerancesProducts.get(4).getGeneric_name());
            basedInIntolerancesQuantity[4].setText(
                basedIntoIntolerancesProducts.get(4).getPackaging() + " "
                    + basedIntoIntolerancesProducts.get(4).getQuantity());
            basedInIntolerances[4].setOnClickListener(new OnClickListener() {
              @Override
              public void onClick(View v) {
                Intent in = new Intent(getActivity(), ActivityProductView.class);
                in.putExtra("product", basedIntoIntolerancesProducts.get(4));
                startActivity(in);
              }
            });
            break;
          default:
            basedInIntolerances[0].setVisibility(View.INVISIBLE);
            basedInIntolerances[1].setVisibility(View.GONE);
            basedInIntolerances[2].setVisibility(View.GONE);
            basedInIntolerances[3].setVisibility(View.GONE);
            basedInIntolerances[4].setVisibility(View.GONE);
            viewMore1.setVisibility(View.GONE);
            noBasedInIntolerances.setVisibility(View.VISIBLE);
            break;
        }
      }
      for (int i = filledLayouts; i < 5; i++) {
        basedInIntolerances[i].setVisibility(View.GONE);
      }
    } else {
      basedInIntolerances[0].setVisibility(View.INVISIBLE);
      basedInIntolerances[1].setVisibility(View.GONE);
      basedInIntolerances[2].setVisibility(View.GONE);
      basedInIntolerances[3].setVisibility(View.GONE);
      basedInIntolerances[4].setVisibility(View.GONE);
      viewMore1.setVisibility(View.GONE);
      noBasedInIntolerances.setVisibility(View.VISIBLE);
    }
  }

  private void showFeatured(View root) {
    String userUid = user.getUid();
    final ObjectMapper mapper = new ObjectMapper();
    List<Featured> featuredList = new ArrayList<>();
    db.collection("FEATURED").get().addOnCompleteListener(
        new OnCompleteListener<QuerySnapshot>() {
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
            Runnable runnable = new Runnable() {
              @Override
              public void run() {
                final ObjectMapper mapper = new ObjectMapper();
                for (QueryDocumentSnapshot document : task.getResult()) {
                  Map<String, Object> objectMap = document.getData();
                  Featured featured = mapper.convertValue(objectMap, Featured.class);
                  featuredList.add(featured);
                }

                root.post(new Runnable() {
                  @Override
                  public void run() {
                    showFeatured2(root, featuredList);
                  }
                });

              }
            };
            new Thread(runnable).start();
          }
        });
  }

  private void showFeatured2(View root, List<Featured> featuredList) {

    for (int i = 0; i < featuredList.size(); i++) {
      switch (i) {
        case 0:
          featured_images[0] = root.findViewById(R.id.image_rectangle1);
          featured_titles[0] = root.findViewById(R.id.title_rectangle1);
          featured_subtitles[0] = root.findViewById(R.id.subtitle_rectangle1);
          Picasso.get().load(featuredList.get(0).getFeatured_image()).fit().centerCrop()
              .into(featured_images[0]);
          featured_titles[0].setText(featuredList.get(0).getFeatured_title());
          featured_subtitles[0].setText(featuredList.get(0).getFeatured_subtitle());
          featuredLayouts[0].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent in = new Intent(getActivity(), ActivityProductsListView.class);
              Bundle args = new Bundle();
              args.putSerializable("ARRAYLIST",
                  (Serializable) featuredList.get(0).getFeatured_products());
              in.putExtra("featuredProducts", args);
              startActivity(in);
            }
          });
          break;
        case 1:
          featured_images[1] = root.findViewById(R.id.image_rectangle2);
          featured_titles[1] = root.findViewById(R.id.title_rectangle2);
          featured_subtitles[1] = root.findViewById(R.id.subtitle_rectangle2);
          featured_titles[1].setText(featuredList.get(1).getFeatured_title());
          featured_subtitles[1].setText(featuredList.get(1).getFeatured_subtitle());
          Picasso.get().load(featuredList.get(1).getFeatured_image()).fit().centerCrop()
              .into(featured_images[1]);
          featuredLayouts[1].setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent in = new Intent(getActivity(), ActivityProductsListView.class);
              in.putExtra("show", "featuredProducts");
              Bundle args = new Bundle();
              args.putSerializable("ARRAYLIST",
                  (Serializable) featuredList.get(1).getFeatured_products());
              in.putExtra("featuredProducts", args);
              startActivity(in);
            }
          });
          break;
      }
    }
  }
}