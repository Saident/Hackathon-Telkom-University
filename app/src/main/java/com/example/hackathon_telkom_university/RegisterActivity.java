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

public class RegisterActivity extends AppCompatActivity {

    protected FirebaseAuth mAuth;
    protected FirebaseDatabase firebaseDatabase;

    protected EditText et_fullname, et_username, et_email,
            et_password, et_confirm_password;

    protected Button register;
    protected ImageView google_login, facebook_login;
    protected TextView regtologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        //initialize
        et_fullname = findViewById(R.id.fullname);
        et_username = findViewById(R.id.username);
        et_email = findViewById(R.id.email);
        et_password = findViewById(R.id.password);
        et_confirm_password = findViewById(R.id.confirm_password);

        register = findViewById(R.id.button_register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {registerUser();}});

        google_login = findViewById(R.id.google_login);
        facebook_login = findViewById(R.id.facebook_login);

        regtologin = findViewById(R.id.tv_regtologin);
        regtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });
        //end of initialize

    }

    protected void registerUser(){
        String fullname = et_fullname.getText().toString().trim();
        String username = et_username.getText().toString().trim();
        String email = et_email.getText().toString().trim();
        String password = et_password.getText().toString().trim();
        String confpass = et_confirm_password.getText().toString().trim();

        //check
        if (fullname.isEmpty()){
            et_fullname.setError("Full name is required");
            et_fullname.requestFocus();
            return;
        }

        if (username.isEmpty()){
            et_username.setError("Username is required");
            et_username.requestFocus();
            return;
        }

        if (email.isEmpty()){
            et_email.setError("Email is required");
            et_email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            et_email.setError("Please use a valid email");
            et_email.requestFocus();
            return;
        }

        if (password.isEmpty()){
            et_password.setError("Password is required");
            et_password.requestFocus();
            return;
        }

        if(password.length() < 8){
            et_password.setError("Password needs to be 8 characters or longer");
            et_password.requestFocus();
            return;
        }

        if(confpass.equals(password)){
        }else{
            et_confirm_password.setError("Password doesnt match");
            et_confirm_password.requestFocus();
            return;
        }
        //end of check
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            User user = new User(fullname, username, email);

                            firebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()){
                                                Toast.makeText(RegisterActivity.this, "User has been created!", Toast.LENGTH_SHORT).show();
                                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                            }else{
                                                Toast.makeText(RegisterActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }else{
                            Toast.makeText(RegisterActivity.this, "Failed to register!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}