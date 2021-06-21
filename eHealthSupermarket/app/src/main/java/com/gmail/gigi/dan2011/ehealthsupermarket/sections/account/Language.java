package com.gmail.gigi.dan2011.ehealthsupermarket.sections.account;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import java.util.Locale;

public class Language extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_language);

    LinearLayout spanish = findViewById(R.id.spanish_lang);
    spanish.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Locale locale = new Locale("es", "ES");
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(locale);
        res.updateConfiguration(conf, dm);
        Intent intent=getIntent();
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
      }
    });

    LinearLayout english = findViewById(R.id.english_lang);
    english.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        Locale locale = new Locale("en", "GB");
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(locale);
        res.updateConfiguration(conf, dm);
        Intent intent=getIntent();
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
      }
    });
  }
}