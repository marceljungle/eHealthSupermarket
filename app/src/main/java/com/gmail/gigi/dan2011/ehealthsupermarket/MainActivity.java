package com.gmail.gigi.dan2011.ehealthsupermarket;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.gmail.gigi.dan2011.ehealthsupermarket.ui.list.mylistFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "com.gmail.gigi.dan2011.ehealthsupermarket.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        //EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }

    public void startAccountSetting(View view) {
        Intent intent = new Intent(this, accountSetting.class);
        //EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        //String message = editText.getText().toString();
        //intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}