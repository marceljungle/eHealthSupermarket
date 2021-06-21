package com.gmail.gigi.dan2011.ehealthsupermarket.sections.scan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gigi.dan2011.ehealthsupermarket.sections.product.ActivityProductView;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.gmail.gigi.dan2011.ehealthsupermarket.collections.Product;
import com.gmail.gigi.dan2011.ehealthsupermarket.sections.product.AndroidCameraApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Javadoc comment.
 */
public class ScanFragment extends Fragment {

  private FirebaseFirestore db = FirebaseFirestore.getInstance();
  private FirebaseUser user;
  private MaterialButton qrButton; // or Button type if not working
  private MaterialButton smartButton;
  private Button viewAll;

  /**
   * Javadoc comment.
   */
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_scan, container, false);

    // Get current user
    user = FirebaseAuth.getInstance().getCurrentUser();

    // Get the id of the views
    qrButton = root.findViewById(R.id.qrButton);
    smartButton = root.findViewById(R.id.smartButton);

    /**
     * Click on QR Button.
     */
    qrButton.setOnClickListener(qrOnclickListener);

    smartButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent act = new Intent(getActivity(), AndroidCameraApi.class);
        startActivity(act);
      }
    });

    return root;
  }


  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    if (result != null) {
      if (result.getContents() != null) {
        Intent intent = new Intent(getContext(), ActivityProductView.class);
        //FIND PRODUCT IN THE FIREBASE DATABASE
        final ObjectMapper mapper = new ObjectMapper();
        db.collection("PRODUCTS").whereEqualTo("code", result.getContents()).get()
            .addOnCompleteListener(
                new OnCompleteListener<QuerySnapshot>() {
                  @Override
                  public void onComplete(Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                      Map<String, Object> rawProduct = new HashMap<>();
                      Product product = new Product();
                      List<Product> productsAlreadyInList = new ArrayList<>();
                      for (QueryDocumentSnapshot document : task.getResult()) {
                        rawProduct = document.getData();
                        product = mapper.convertValue(rawProduct, Product.class);
                      }
                      intent.putExtra("product", product);
                      startActivity(intent);
                    }
                    // tvBarCode.setText("El código de barras es:\n" + result.getContents());
                    else {
                      //tvBarCode.setText("Error al escanear el código de barras");
                      Toast.makeText(getContext(), "Error al escanear el código de barras",
                          Toast.LENGTH_SHORT).show();
                    }
                  }
                });
      }
    }
  }

  private View.OnClickListener qrOnclickListener = new View.OnClickListener() {
    @Override
    public void onClick(View v) {
      IntentIntegrator integrator = IntentIntegrator.forSupportFragment(ScanFragment.this);
      integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
      integrator.setPrompt("Scan!!");
      integrator.setCameraId(0);
      integrator.setBeepEnabled(false);
      integrator.setBarcodeImageEnabled(true);
      integrator.initiateScan();
    }
  };
}