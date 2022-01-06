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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class Login extends AppCompatActivity {
    EditText email, password;
    Button btnLogin;
    TextView createAccount;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/RobotoMono-Regular.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());
        password = findViewById(R.id.updatePass);
        email = findViewById(R.id.updateEmail);
        btnLogin = findViewById(R.id.saveEdit);
        createAccount = findViewById(R.id.newAccount);
        firebaseAuth = FirebaseAuth.getInstance();


//        User user = new User("user3","pass2");
//
//
//        db.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("firestore", "Users added: " + documentReference.getId());
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Log.w("firestore", "Error adding document", e);
//            }
//        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (internetConnectionStatus.checkInternet(Login.this)) {
                    String inputEmail = email.getText().toString().trim();
                    String inputPassword = password.getText().toString().trim();
                    if (TextUtils.isEmpty(inputEmail)) {
                        email.setError("Email  required ! ");
                    }
                    if (TextUtils.isEmpty(inputPassword)) {
                        password.setError("Pass  required ! ");
                    }
                    if (password.length() < 2) {
                        password.setError("Password must contain at least 8 characters");
                        return;
                    }
                    login(inputEmail, inputPassword);
//                    firebaseAuth.signInWithEmailAndPassword(inputEmail, inputPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if (task.isSuccessful()) {
//                                Toast.makeText(Login.this, "Login success", Toast.LENGTH_SHORT).show();
//                                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
//                                intent.putExtra("email",inputEmail);
//                                intent.putExtra("pass",inputPassword);
//                                startActivity(intent);
//                            } else {
//                                Toast.makeText(Login.this, "Email or passsword wrong ", Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });
                } else {
                    Snackbar.make(v, "You dont have internet", Snackbar.LENGTH_LONG).setAction("Go offline", new loginOfflineListener()).show();
                }
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    Boolean toastTest = false;

    public void login(String username, String password) {
        Log.d("decrypt", new Vigenere().originalText("KUDHAJLOB"));
        Vigenere passwordEncoder = new Vigenere();

        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("userGet", document.getId() + " => " + document.getData().get("username"));
                                if (username.equals(document.getData().get("username")) &&
                                        passwordEncoder.encryptPass(password).equals(document.getData().get("password").toString())) {
                                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                                    intent.putExtra("email", username);
                                    intent.putExtra("pass", password);
                                    toastTest = true;
                                    startActivity(intent);
                                }
                            }
                            displayToast(toastTest);
                        } else {
                            Log.w("userGet", "Error getting documents.", task.getException());
                        }
                    }
                });

    }

    public void displayToast(Boolean response) {
        if (response) {
            Toast.makeText(Login.this, "Login success", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(Login.this, "Email or passsword wrong ", Toast.LENGTH_SHORT).show();
        }
    }

}