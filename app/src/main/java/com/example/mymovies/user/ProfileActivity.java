package com.example.mymovies.user;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.ToggleButton;

import com.example.mymovies.activities.MainActivity;
import com.example.mymovies.R;
import com.google.firebase.auth.FirebaseAuth;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class ProfileActivity extends AppCompatActivity {
    EditText email;
    EditText password;
   Button toMain;
   Button signout;
   ToggleButton showPass;
   Switch  aSwitch;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        email = findViewById(R.id.editEmail);
        password = findViewById(R.id.editPass);
        toMain = findViewById(R.id.goMain);
        signout = findViewById(R.id.btnLogout);
        showPass = findViewById(R.id.showPass);
        aSwitch = findViewById(R.id.switch1);
        String pass = " ";
        if (getIntent().getStringExtra("pass") != null) {
            pass += getIntent().getStringExtra("pass");
            email.append(getIntent().getStringExtra("email"));
        }
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
         if(isChecked){
             getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
         }else{
             getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
         }
            }
        });
        for (int i = 0; i < 8; i++) password.append("*");
        toMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
               intent.putExtra("email",getIntent().getStringExtra("email"));
               intent.putExtra("pass",getIntent().getStringExtra("pass"));
                startActivity(intent);
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
                 password.setText("Pass: "+ finalPass);
                 showPass.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility_off));
             }
               if(!isChecked){
                   password.setText("Pass: ");
                   for(int i = 0; i< 8; i++) password.append("*");
                   showPass.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_baseline_visibility));
               }

            }
        });
    }
}