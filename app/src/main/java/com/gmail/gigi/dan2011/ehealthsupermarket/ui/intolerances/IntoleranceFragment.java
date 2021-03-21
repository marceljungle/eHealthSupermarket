package com.gmail.gigi.dan2011.ehealthsupermarket.ui.intolerances;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gigi.dan2011.ehealthsupermarket.AddIntolerancesOrAdditives;
import com.gmail.gigi.dan2011.ehealthsupermarket.AddIntolerancesOrAdditivesAdapter;
import com.gmail.gigi.dan2011.ehealthsupermarket.AddIntolerancesOrAdditivesXAdapter;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
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
  private FragmentTransaction ft;
  private IntoleranceFragment actualFragment;
  private IntoleranceFragment anotherFragment;
  private View rootView;

  /**
   * Javadoc comment.
   */
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    user = FirebaseAuth.getInstance().getCurrentUser();
    intoleranceViewModel = new ViewModelProvider(this).get(IntoleranceViewModel.class);
    View root = inflater.inflate(R.layout.fragment_intolerances, container, false);
    importUserIntolerances(root);
    importUserAdditives(root);
    listShow = root.findViewById(R.id.listshowIntolerances);
    listShow.setHasFixedSize(true);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    listShow.setLayoutManager(linearLayoutManager);
    floatingActionButton = root.findViewById(R.id.fabIntolerances);
    ft = getParentFragmentManager().beginTransaction();
    actualFragment = this;
    anotherFragment = new IntoleranceFragment();
    floatingActionButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getActivity(), AddIntolerancesOrAdditives.class);
        onPause();
        startActivity(intent);
      }
    });
    rootView = root;
    return root;
  }

  @Override
  public void onResume() {
    super.onResume();
    importUserIntolerances(rootView);
    importUserAdditives(rootView);
  }

  private void importUserAdditives(View root) {
    db.collection("USERS").document(user.getUid()).collection("unsupported_additives").get()
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
              } //TODO: change this as the intolerance method below
              addIntolerancesOrAdditivesAdapter = new AddIntolerancesOrAdditivesXAdapter(mapOfItems,
                  root.getContext(), user, db);
              listShow.setAdapter(addIntolerancesOrAdditivesAdapter);
              addIntolerancesOrAdditivesAdapter.notifyDataSetChanged();
            }
          }
        });
  }

  private void importUserIntolerances(View root) {
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
                addIntolerancesOrAdditivesAdapter = new AddIntolerancesOrAdditivesXAdapter(
                    mapOfItems,
                    root.getContext(), user, db);
                listShow.setAdapter(addIntolerancesOrAdditivesAdapter);
                addIntolerancesOrAdditivesAdapter.notifyDataSetChanged();
              }
            }
          }
        });
  }

}
