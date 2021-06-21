package com.gmail.gigi.dan2011.ehealthsupermarket.sections.account;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.gmail.gigi.dan2011.ehealthsupermarket.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Javadoc comment.
 */
public class ResetPasswordActivity extends AppCompatActivity {


  private EditText inputEmail;
  private Button btnReset;
  private FirebaseAuth mauth;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_reset_password);

    //Get objects references
    inputEmail = (EditText) findViewById(R.id.emailEditText);
    btnReset = (Button) findViewById(R.id.reset_button);
    // Get firebase authentication instance
    mauth = FirebaseAuth.getInstance();

    //click on reset_button
    btnReset.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //Get EditText value
        String email = inputEmail.getText().toString().trim();
        //Validate if email has been entered
        if (TextUtils.isEmpty(email)) {
          Toast.makeText(getApplicationContext(), "¡Introducir la dirección de correo electrónico!",
              Toast.LENGTH_SHORT).show();
          return;
        }

        //Reset password
        mauth.sendPasswordResetEmail(email)
            .addOnCompleteListener(ResetPasswordActivity.this, new OnCompleteListener<Void>() {
              @Override
              public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                  Toast.makeText(ResetPasswordActivity.this,
                      "Le hemos enviado instrucciones para restablecer su contraseña",
                      Toast.LENGTH_SHORT).show();
                  finish();
                } else {
                  Toast.makeText(ResetPasswordActivity.this,
                      "Error al enviar el correo electrónico de reinicio", Toast.LENGTH_SHORT)
                      .show();
                }
              }
            });
      }
    });
  }
}