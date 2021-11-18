package com.example.helping_hand;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.helping_hand.Login.Login;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();


        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {

            @Override
            public void run() {
               moveToNextScreen();
            }

        }, 1500L);
    }

    private void moveToNextScreen() {
        if (mAuth.getCurrentUser() != null) {
            Intent intsplash=new Intent(SplashActivity.this, HomeScreen.class);
            startActivity(intsplash);
        } else {
            Intent intsplash=new Intent(SplashActivity.this, Login.class);
            startActivity(intsplash);
        }
        finish();
    }
}