package com.ajmalyousufza.mygroceryshoppingcart.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ajmalyousufza.mygroceryshoppingcart.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    private FirebaseAuth fauth;
    Button sign_in;
    TextView sign_up;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fauth = FirebaseAuth.getInstance();

        email = findViewById(R.id.email_login);
        password = findViewById(R.id.password_login);
        sign_in = findViewById(R.id.login_btn_login);
        sign_up = findViewById(R.id.sign_up_login);
        progressBar = findViewById(R.id.progressbar_login);
        progressBar.setVisibility(View.GONE);

        if(fauth.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

        sign_in.setOnClickListener(view -> {

            progressBar.setVisibility(View.VISIBLE);

            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();

            if(TextUtils.isEmpty(userEmail)){
                Toast.makeText(LoginActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(userPassword)){
                Toast.makeText(LoginActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }
            if(userPassword.length()<6){
                Toast.makeText(LoginActivity.this, "Password Must have 6 or more charecters", Toast.LENGTH_SHORT).show();
                return;
            }

            fauth.signInWithEmailAndPassword(userEmail,userPassword)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Login successfull", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "Login Failed - "+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        });

        sign_up.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });
    }
}