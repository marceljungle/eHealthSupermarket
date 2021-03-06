package com.gmail.gigi.dan2011.ehealthsupermarket;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.gmail.gigi.dan2011.ehealthsupermarket.dbCollections.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class SignupActivity extends AppCompatActivity {

    //declare variables
    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp;
    private FirebaseAuth auth;

    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        db = FirebaseFirestore.getInstance();


        // Get firebase authentication instance
        auth = FirebaseAuth.getInstance();

        //Get objects references
        btnSignIn = (Button) findViewById(R.id.signIn_button);
        btnSignUp = (Button) findViewById(R.id.reset_button);
        inputEmail = (EditText) findViewById(R.id.emailEditText);
        inputPassword = (EditText) findViewById(R.id.passwordEditTextT);

        //Click on login button
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get EditText values
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                //Validate if email has been entered
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "¡Introducir la dirección de correo electrónico!", Toast.LENGTH_SHORT).show();
                    return;
                }
                //Validate if password has been entered
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "¡Introducir la contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Contraseña demasiado corta, ingrese un mínimo de 6 carácteres", Toast.LENGTH_SHORT).show();
                    return;
                }


                //Create user
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignupActivity.this, "CreateUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();

                        //show message if authentication failed
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "Autenticación fallida" + task.getException(), Toast.LENGTH_SHORT).show();
                        } else {
                            User user = new User("Change me", "Change me", "Change me", "Change me", "Change me", new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), inputEmail.getText().toString(), auth.getCurrentUser().getUid());
                            CollectionReference users = db.collection("USERS");
                            String idUser = auth.getCurrentUser().getUid();
                            users.document(idUser).set(user).addOnSuccessListener(new OnSuccessListener() {
                                @Override
                                public void onSuccess(Object o) {
                                    Toast.makeText(SignupActivity.this, "Your Course has been added to Firebase Firestore", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // this method is called when the data addition process is failed.
                                    // displaying a toast message when data addition is failed.
                                    Toast.makeText(SignupActivity.this, "Fail to add course \n" + e, Toast.LENGTH_SHORT).show();
                                }
                            });


                            startActivity(new Intent(SignupActivity.this, MainActivity.class));
                            finish();

                        }
                    }

                });
            }
        });

    }
}

