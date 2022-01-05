package com.ajmalyousufza.mygroceryshoppingcart.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ajmalyousufza.mygroceryshoppingcart.R;
import com.google.firebase.auth.FirebaseAuth;

public class SplashScreenActivity extends AppCompatActivity {

    LinearLayout login_,regi_;
    ProgressBar progressBar;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        fauth = FirebaseAuth.getInstance();
        login_ = findViewById(R.id.login_splash);
        regi_ = findViewById(R.id.register_splash);
        progressBar = findViewById(R.id.progressbar_splash);
        progressBar.setVisibility(View.GONE);

        if(fauth.getCurrentUser()!=null){
            Toast.makeText(SplashScreenActivity.this, "You Already registered", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        }

        login_.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(SplashScreenActivity.this, LoginActivity.class));
            finish();
        });
        regi_.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            startActivity(new Intent(SplashScreenActivity.this, RegistrationActivity.class));
            finish();
        });
    }
}