package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


/**
 * Javadoc comment.
 */
public class LoginActivity extends AppCompatActivity {

  private EditText inputEmail;
  private EditText inputPassword;
  private FirebaseAuth auth;
  private Button btnLogin;
  private Button btnReset;
  private Button btnSignup;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Get firebase authentication instance
    auth = FirebaseAuth.getInstance();

    //if the instance is other than null
    if (auth.getCurrentUser() != null) {
      startActivity(new Intent(LoginActivity.this, MainActivity.class));
      finish();
    }

    //Set view now
    setContentView(R.layout.activity_login);

    // Get the drivers reference

    inputEmail = findViewById(R.id.emailEditText);
    inputPassword = findViewById(R.id.passwordEditTextT);
    btnSignup = findViewById(R.id.signUpButton);
    btnLogin = findViewById(R.id.logInButton);
    btnReset = findViewById(R.id.reset_button);

    // Get firebase authentication instance
    auth = FirebaseAuth.getInstance();

    //Click on login button
    btnLogin.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {

        // Get EditText values
        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();

        //Validate if email has been entered
        if (TextUtils.isEmpty(email)) {
          Toast.makeText(getApplicationContext(), "¡Introducir la dirección de correo electrónico!",
              Toast.LENGTH_SHORT).show();
          return;
        }
        //Validate if password has been entered
        if (TextUtils.isEmpty(password)) {
          Toast.makeText(getApplicationContext(), "¡Introducir la contraseña!", Toast.LENGTH_SHORT)
              .show();
          return;
        }

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this,
            new OnCompleteListener<AuthResult>() {
              @Override
              public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                  // There's been a problem
                  if (password.length() < 6) {
                    inputPassword.setError(getString(R.string.minimum_password));
                  } else {
                    Toast.makeText(LoginActivity.this, getString(R.string.auth_failed),
                        Toast.LENGTH_SHORT).show();
                  }
                } else {
                  Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                  startActivity(intent);
                  finish();
                }
              }
            });
      }
    });

    //Click on register button
    btnSignup.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(LoginActivity.this, SignupActivity.class));
      }
    });

    //Click on forgot password button
    btnReset.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
        startActivity(intent);
      }
    });
  }
}