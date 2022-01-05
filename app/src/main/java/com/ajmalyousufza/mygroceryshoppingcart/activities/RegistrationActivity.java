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
import com.ajmalyousufza.mygroceryshoppingcart.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {

    Button sign_up;
    EditText name,email,password;
    TextView sign_in;
    private FirebaseAuth auth;
    FirebaseDatabase database;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        sign_up = findViewById(R.id.login_btn);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        sign_in = findViewById(R.id.sign_in);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

//        if(auth.getCurrentUser()!=null){
//            startActivity(new Intent(RegistrationActivity.this,MainActivity.class));
//            finish();
//        }

        sign_up.setOnClickListener(view -> {

            progressBar.setVisibility(View.VISIBLE);

            String userName = name.getText().toString();
            String userEmail = email.getText().toString();
            String userPassword = password.getText().toString();

            if(TextUtils.isEmpty(userName)){
                Toast.makeText(RegistrationActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(userEmail)){
                Toast.makeText(RegistrationActivity.this, "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if(TextUtils.isEmpty(userPassword)){
                Toast.makeText(RegistrationActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                return;
            }
            if(userPassword.length()<6){
                Toast.makeText(RegistrationActivity.this, "Password Must have 6 or more charecters", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.createUserWithEmailAndPassword(userEmail,userPassword)
                    .addOnCompleteListener(RegistrationActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                UserModel userModel = new UserModel(userName,userEmail,userPassword);
                                String id = task.getResult().getUser().getUid();
                                database.getReference().child("Users").child(id).setValue(userModel);
                                Toast.makeText(RegistrationActivity.this, "Successfully added", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                startActivity(new Intent(RegistrationActivity.this, MainActivity.class));
                                finish();
                            }else{
                                Toast.makeText(RegistrationActivity.this, "Registration Failed - "+task.getException(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        });

        sign_in.setOnClickListener(view -> {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        });
    }



}