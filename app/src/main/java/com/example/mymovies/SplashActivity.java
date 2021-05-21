package com.example.mymovies;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.mymovies.user.Login;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView lottieAnimationView;
    ImageView title;
    private Handler delay = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        lottieAnimationView = findViewById(R.id.animation);
        title = findViewById(R.id.titleSplash);
        title.animate().translationY(-1600).setDuration(1000).setStartDelay(2000);
        lottieAnimationView.animate().translationY(2000).setDuration(1000).setStartDelay(2000);
        delay.postDelayed(new Runnable() {
            @Override
            public void run() {
                try{
                    startActivity(new Intent(SplashActivity.this, Login.class));
                    finish();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        },2800);


    }
}