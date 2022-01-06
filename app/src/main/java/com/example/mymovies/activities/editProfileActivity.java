package com.example.mymovies.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymovies.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class editProfileActivity  extends AppCompatActivity {

        EditText email;
        EditText password;
        Button saveupdate;

        @Override
        public void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_edit_profile);
               email  =findViewById(R.id.updateEmail);
               password  =findViewById(R.id.updatePass);
               saveupdate = findViewById(R.id.saveEdit);
               email.setText(getIntent().getStringExtra("email"));
               password.setText(getIntent().getStringExtra("pass"));
               saveupdate.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {
                               FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                               String newEmail = email.getText().toString();
                               String newPass = password.getText().toString();
                               user.updateEmail(newEmail);
                               user.updatePassword(newPass);
                               Intent intent = new Intent(editProfileActivity.this, ProfileActivity.class);
                               intent.putExtra("email",newEmail);
                               intent.putExtra("pass",newPass);
                               Toast.makeText(editProfileActivity.this, "User details updated ! ", Toast.LENGTH_SHORT).show();
                               startActivity(intent);

                       }
               });

        }
}