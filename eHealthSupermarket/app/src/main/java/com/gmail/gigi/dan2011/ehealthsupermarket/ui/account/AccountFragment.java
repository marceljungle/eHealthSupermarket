package com.gmail.gigi.dan2011.ehealthsupermarket.ui.account;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.gmail.gigi.dan2011.ehealthsupermarket.AddIntolerancesOrAdditives;
import com.gmail.gigi.dan2011.ehealthsupermarket.LoginActivity;
import com.gmail.gigi.dan2011.ehealthsupermarket.ProfileActivity;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.gmail.gigi.dan2011.ehealthsupermarket.ui.list.MyListProducts;
import com.gmail.gigi.dan2011.ehealthsupermarket.ui.list.RowItem;
import com.google.api.Distribution.BucketOptions.Linear;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Javadoc comment.
 */
public class AccountFragment extends Fragment {

  private AccountViewModel accountViewModel;
  private FirebaseAuth mauth;

  /**
   * Javadoc comment.
   */
  public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    accountViewModel = new ViewModelProvider(this).get(AccountViewModel.class);
    View root = inflater.inflate(R.layout.fragment_account, container, false);

    //Get Auth Firebase reference
    mauth = FirebaseAuth.getInstance();
    //Get current user
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    //Get objects references
    LinearLayout rowLogOut = (LinearLayout) root.findViewById(R.id.individual_row_log_out);
    //Click on log_out
    rowLogOut.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (user != null) {
          mauth.signOut();
          Intent intent = new Intent(getActivity(), LoginActivity.class);
          startActivity(intent);
        }
      }
    });
    LinearLayout language = root.findViewById(R.id.individual_row_language);
    language.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getContext(), Language.class);
        startActivity(intent);
      }
    });

    LinearLayout profile = root.findViewById(R.id.individual_row_personal_data);
    profile.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getContext(), ProfileActivity.class);
        startActivity(intent);
      }
    });
    LinearLayout help = root.findViewById(R.id.individual_row_help);
    help.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Uri uri = Uri.parse("https://github.com/marceljungle/eHealthSupermarket");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
      }
    });
    LinearLayout suggestions = root.findViewById(R.id.individual_row_suggestions);
    suggestions.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Uri uri = Uri.parse("https://github.com/marceljungle/eHealthSupermarket");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
      }
    });
    return root;
  }
}
