package com.example.hackathon_telkom_university;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Third_EditProfileActivity extends AppCompatActivity {

    EditText fullname, username, email, phone;
    Button save;
    Class_User user = new Class_User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        fullname = findViewById(R.id.fullname);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.getValue(Class_User.class);
                fullname.setText(user.fullname);
                username.setText(user.username);
                email.setText(user.email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        save = findViewById(R.id.button_save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setEmail(String.valueOf(email.getText()));
                user.setFullname(String.valueOf(fullname.getText()));
                user.setUsername(String.valueOf(username.getText()));
                user.setPhone(String.valueOf(phone.getText()));
                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance()
                        .getCurrentUser().getUid()).setValue(user);

                Toast.makeText(Third_EditProfileActivity.this, "Saved", Toast.LENGTH_SHORT);
            }
        });

        botNav();
    }

    protected void botNav(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.search:
                        startActivity(new Intent(getApplicationContext(), Second_SearchActivity.class));
                        overridePendingTransition(0,0);
                        onPause();
                        return true;
                    case R.id.profile:
                        return true;
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Second_HomeActivity.class));
                        overridePendingTransition(0,0);
                        onPause();
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onPause() {
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.profile);
        super.onPause();
    }
}