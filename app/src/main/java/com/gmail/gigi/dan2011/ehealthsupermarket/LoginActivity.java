package com.gmail.gigi.dan2011.ehealthsupermarket;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.sql.DatabaseMetaData;


public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnSignup, btnLogin, btnReset;


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

        inputEmail = (EditText) findViewById(R.id.emailEditText);
        inputPassword = (EditText) findViewById(R.id.passwordEditTextT);
        btnSignup = (Button) findViewById(R.id.signUpButton);
        btnLogin = (Button) findViewById(R.id.logInButton);
        btnReset = (Button) findViewById(R.id.signup_button);

        // Get firebase authentication instance
        auth = FirebaseAuth.getInstance();

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

                // Get EditText values
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                //Validate if email has been entered
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(), "¡Introducir la dirección de correo electrónico!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Validate if password has been entered
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "¡Introducir la contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //Verified exist user
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            // There's been a problem
                            if (password.length() < 6){
                                inputPassword.setError(getString(R.string.minimum_password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_SHORT).show();
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

    }
}