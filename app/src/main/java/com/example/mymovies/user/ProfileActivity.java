package com.example.mymovies.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.mymovies.MainActivity;
import com.example.mymovies.R;
import com.google.firebase.auth.FirebaseAuth;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ProfileActivity extends AppCompatActivity {
    TextView email;
    TextView password;
   Button toMain;
   Button signout;
   ToggleButton showPass;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email = findViewById(R.id.userEmail);
        password = findViewById(R.id.userPass);
        toMain = findViewById(R.id.goMain);
        signout = findViewById(R.id.btnLogout);
        showPass = findViewById(R.id.showPass);
        String pass =" ";
        if(getIntent().getStringExtra("pass") != null){
            pass+= getIntent().getStringExtra("pass");
            email.append(getIntent().getStringExtra("email"));
        }

        for(int i = 0;i<8;i++) password.append("*");
        toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
        String finalPass = pass;
        showPass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
             if(isChecked) {
                 password.setText("Password: "+ finalPass);
                 showPass.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility_off));
             }
               if(!isChecked){
                   password.setText("Password: ");
                   for(int i = 0; i< 8; i++) password.append("*");
                   showPass.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility));
               }

            }
        });
    }
}