package com.example.mymovies.user;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovies.R;
import com.example.mymovies.activities.ProfileActivity;
import com.example.mymovies.listener.loginOfflineListener;
import com.example.mymovies.network.internetConnectionStatus;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class Register extends AppCompatActivity {
    EditText  name, password,email;
    TextView login;
    Button registerBtn;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        name = findViewById(R.id.inputName);
        email = findViewById(R.id.inputEmail);
        password = findViewById(R.id.inputPass);
        registerBtn = findViewById(R.id.buttonRegister);
        login = findViewById(R.id.alreadyLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
            Toast.makeText(Register.this, "Already logged in, please sign out", Toast.LENGTH_SHORT).show();
            finish();
        }
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internetConnectionStatus.checkInternet(Register.this)) {
                    String inputEmail = email.getText().toString().trim();
                    String inputPass = password.getText().toString().trim();
                    if (TextUtils.isEmpty(inputEmail)) {
                        email.setError("Email  required ! ");
                    }
                    if (TextUtils.isEmpty(inputPass)) {
                        password.setError("Pass  required ! ");
                    }
                    if (password.length() < 8) {
                        password.setError("Password must contain at least 8 characters");
                        return;
                    }
                  register(inputEmail,new Vigenere().encryptPass(inputPass.toUpperCase()));
//                    firebaseAuth.createUserWithEmailAndPassword(inputEmail, inputPass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(Register.this, "User Added", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
//                                intent.putExtra("email", inputEmail);
//                                intent.putExtra("pass", inputPass);
//                               //firestore test
//
//                                startActivity(intent);
//
//                            } else {
//                                Toast.makeText(Register.this, "Error" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });
                } else {
                    Snackbar.make(v, "You dont have internet", Snackbar.LENGTH_LONG).setAction("Go offline", new loginOfflineListener()).show();
                }
                //
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }
    public void register(String username, String password){
        db.collection("users")
                .add(new User(username,password))
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(Register.this, "User Added", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                intent.putExtra("email", username);
                                intent.putExtra("pass", password);
                               //firestore test
                                startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Error" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}