package com.gmail.gigi.dan2011.ehealthsupermarket.sections.intolerances;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gigi.dan2011.ehealthsupermarket.sections.intolerances.AddIntolerancesOrAdditivesXAdapter.ClickListener;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Additive;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Intolerance;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

/**
 * Javadoc comment.
 */
public class IntoleranceFragment extends Fragment {

  private IntoleranceViewModel intoleranceViewModel;
  private RecyclerView listShow;
  private FirebaseFirestore db = FirebaseFirestore.getInstance();
  private AddIntolerancesOrAdditivesXAdapter addIntolerancesOrAdditivesAdapter;
  private Map<String, Object> mapOfItems = new HashMap<>();
  private FirebaseUser user;
  private FloatingActionButton floatingActionButton;
  private View rootView;

  /**
   * Javadoc comment.
   */
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {

    user = FirebaseAuth.getInstance().getCurrentUser();
    intoleranceViewModel = new ViewModelProvider(this).get(IntoleranceViewModel.class);
    View root = inflater.inflate(R.layout.fragment_intolerances, container, false);
    importUserIntolerances();
    importUserAdditives();
    listShow = root.findViewById(R.id.listshowIntolerances);
    listShow.setHasFixedSize(true);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    listShow.setLayoutManager(linearLayoutManager);
    floatingActionButton = root.findViewById(R.id.fabIntolerances);

    floatingActionButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), AddIntolerancesOrAdditives.class);
        startActivity(intent);
      }
    });

    rootView = root;
    return root;
  }

  @Override
  public void onResume() {
    super.onResume();
    importUserIntolerances();
    importUserAdditives();
  }

  private void importUserAdditives() {
    mapOfItems = new HashMap<>();
    db.collection("USERS").document(user.getUid()).get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
              final ObjectMapper mapper = new ObjectMapper();
              Map<String, Object> document = task.getResult().getData();
              User userInfo = mapper.convertValue(document, User.class);
              if (userInfo.getUnsupported_additives() != null && !userInfo
                  .getUnsupported_additives().isEmpty()) {
                for (Additive additive : userInfo.getUnsupported_additives()) {
                  if (additive.getAdditive_name() != null
                      && additive.getAdditive_name() != "") {
                    mapOfItems.put(additive.getAdditive_name(), additive);
                  }
                }
                addIntolerancesOrAdditivesAdapter.notifyDataSetChanged();
              }
            }
          }
        });
  }

  public void importUserIntolerances() {
    mapOfItems = new HashMap<>();
    db.collection("USERS").document(user.getUid()).get()
        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
            if (task.isSuccessful()) {
              final ObjectMapper mapper = new ObjectMapper();
              Map<String, Object> document = task.getResult().getData();
              User userInfo = mapper.convertValue(document, User.class);
              if (userInfo.getIntolerances() != null && !userInfo.getIntolerances().isEmpty()) {
                for (Intolerance intolerance : userInfo.getIntolerances()) {
                  if (intolerance.getIntolerance_name() != null
                      && intolerance.getIntolerance_name() != "") {
                    mapOfItems.put(intolerance.getIntolerance_name(), intolerance);
                  }
                }
              }
              addIntolerancesOrAdditivesAdapter = new AddIntolerancesOrAdditivesXAdapter(
                  mapOfItems,
                  getContext(), user, db);
              addIntolerancesOrAdditivesAdapter.setOnItemClickListener(new ClickListener() {
                @Override
                public void onItemClick(int position, View v) {
                  if (mapOfItems.keySet().toArray().length != 0) {
                    if (mapOfItems.get(mapOfItems.keySet().toArray()[position]).getClass()
                        == Intolerance.class) {
                      db.collection("USERS").document(user.getUid())
                          .update("intolerances",
                              FieldValue.arrayRemove(
                                  mapOfItems.get(mapOfItems.keySet().toArray()[position])));
                      importUserIntolerances();
                      importUserAdditives();
                    }else {
                      db.collection("USERS").document(user.getUid())
                          .update("unsupported_additives",
                              FieldValue.arrayRemove(
                                  mapOfItems.get(mapOfItems.keySet().toArray()[position])));
                      importUserAdditives();
                      importUserIntolerances();
                    }
                  }
                }
              });
              listShow.setAdapter(addIntolerancesOrAdditivesAdapter);
              addIntolerancesOrAdditivesAdapter.notifyDataSetChanged();
            }
          }
        });
  }
}