package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Additive;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Intolerance;
import com.gmail.gigi.dan2011.ehealthsupermarket.ui.intolerances.IntoleranceFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddIntolerancesOrAdditives extends AppCompatActivity {

  private RecyclerView listShow;
  private FirebaseFirestore db = FirebaseFirestore.getInstance();
  private AddIntolerancesOrAdditivesAdapter addIntolerancesOrAdditivesAdapter;
  private SearchView searchView;
  private Map<String, Object> mapOfItems = new HashMap<>();
  private FirebaseUser user;
  private Intent intent;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_add_intolerances_or_additives);
    user = FirebaseAuth.getInstance().getCurrentUser();
    importIntolerances(this);
    importAdditives(this);
    listShow = findViewById(R.id.listshow);
    listShow.setHasFixedSize(true);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
    listShow.setLayoutManager(linearLayoutManager);
    intent = new Intent(AddIntolerancesOrAdditives.this, IntoleranceFragment.class);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.searchfile, menu);
    final MenuItem myActionMenuItem = menu.findItem(R.id.search);
    searchView = (SearchView) myActionMenuItem.getActionView();
    searchView.setFocusable(true);
    searchView.setIconifiedByDefault(false);
    searchView.requestFocus();
    searchView.setOnQueryTextListener(new OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        if (!searchView.isIconified()) {
          searchView.setIconified(true);
        }
        myActionMenuItem.collapseActionView();
        return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        final List<String> filtermodelist = filter(Arrays.asList(mapOfItems.keySet().toArray()),
            newText);
        addIntolerancesOrAdditivesAdapter.setfilter(filtermodelist);
        return true;
      }
    });
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public void onBackPressed() {
    finish();
    super.onBackPressed();
  }

  public void importIntolerances(Context context) {

    db.collection("INTOLERANCES").get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
              final ObjectMapper mapper = new ObjectMapper();
              for (QueryDocumentSnapshot document : task.getResult()) {
                Map<String, Object> objectMap = document.getData();
                Intolerance intolerance = mapper.convertValue(objectMap, Intolerance.class);
                if (intolerance.getIntolerance_name() != null
                    && intolerance.getIntolerance_name() != "") {
                  mapOfItems.put(intolerance.getIntolerance_name(), intolerance);
                }
              }
              addIntolerancesOrAdditivesAdapter = new AddIntolerancesOrAdditivesAdapter(mapOfItems,
                  context, user, db);
              listShow.setAdapter(addIntolerancesOrAdditivesAdapter);
              addIntolerancesOrAdditivesAdapter.notifyDataSetChanged();
            }
          }
        });
  }

  public void importAdditives(Context context) {

    db.collection("ADDITIVES").get()
        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
          @Override
          public void onComplete(@NonNull Task<QuerySnapshot> task) {
            if (task.isSuccessful()) {
              final ObjectMapper mapper = new ObjectMapper();
              for (QueryDocumentSnapshot document : task.getResult()) {
                Map<String, Object> objectMap = document.getData();
                Additive additive = mapper.convertValue(objectMap, Additive.class);
                if (additive.getAdditive_name() != null && additive.getAdditive_name() != "") {
                  mapOfItems.put(additive.getAdditive_name(), additive);
                }
              }
              addIntolerancesOrAdditivesAdapter = new AddIntolerancesOrAdditivesAdapter(mapOfItems,
                  context, user, db);
              listShow.setAdapter(addIntolerancesOrAdditivesAdapter);
              addIntolerancesOrAdditivesAdapter.notifyDataSetChanged();
            }
          }
        });
  }


  private List<String> filter(List<Object> pl, String query) {
    query = query.toLowerCase();
    final List<String> filteredModeList = new ArrayList<>();
    for (Object model : pl) {
      final String text = ((String) model).toLowerCase();
      if (text.startsWith(query)) {
        filteredModeList.add((String) model);
      }
    }
    return filteredModeList;
  }

  private void changeSearchViewTextColor(View view) {
    if (view != null) {
      if (view instanceof TextView) {
        ((TextView) view).setTextColor(Color.WHITE);
        return;
      } else if (view instanceof ViewGroup) {
        ViewGroup viewGroup = (ViewGroup) view;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
          changeSearchViewTextColor(viewGroup.getChildAt(i));
        }
      }
    }
  }
}