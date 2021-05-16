package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Javadoc comment.
 */
public class MainActivity extends AppCompatActivity {

  public static final String EXTRA_MESSAGE = "com.gmail.gigi.dan2011.ehealthsupermarket.MESSAGE";
  private FirebaseAuth auth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Get Auth Firebase reference
    auth = FirebaseAuth.getInstance();
    //Get current user
    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


    /* Bottom navigation menu creation */
    BottomNavigationView navigationView = findViewById(R.id.bottom_navigation);
    AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        R.id.navigation_home, R.id.navigation_mylist, R.id.navigation_scan,
        R.id.navigation_intolerances, R.id.navigation_account
    ).build();

    /* Ubicate the navcontroller created in activity_main.xml */
    NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
    /* Changes top bar title with the label specified in mobile_navigation.xml where all
     * fragments are ubicated */
    NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
    /* Sets up a BottomNavigationView for use with a NavController */
    NavigationUI.setupWithNavController(navigationView, navController);
  }


  /**
   * Javadoc comment.
   */
  public void startAccountSetting(View view) {
    Intent intent = new Intent(this, AccountSettings.class);
    startActivity(intent);
  }
}