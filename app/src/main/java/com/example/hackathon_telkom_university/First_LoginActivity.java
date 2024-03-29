package com.example.hackathon_telkom_university;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class First_LoginActivity extends AppCompatActivity{

    protected FirebaseAuth mAuth;
    protected FirebaseDatabase firebaseDatabase;

    protected EditText username, password;
    protected TextView forgot_password, register;
    protected Button button_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        //initialize
        button_login = findViewById(R.id.button_login);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loginuser();
            }
        });

        forgot_password = findViewById(R.id.forgot_password);
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), First_RegisterActivity.class));
            }
        });
        //end of initialize
    }

    private void Loginuser() {
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(email.isEmpty()){
            username.setError("Email is required!");
            username.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            username.setError("Please enter a valid email");
            username.requestFocus();
            return;
        }
        if(pass.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }
        if(pass.length() < 6){
            password.setError("Password needs to be 6 characters or longer");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(First_LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT);
                            startActivity(new Intent(First_LoginActivity.this, Second_HomeActivity.class));
                            finish();
                        } else {
                            Toast.makeText(First_LoginActivity.this, "Failed to login!", Toast.LENGTH_SHORT);
                            return;
                        }
                    }
                });
    }
}