package com.example.hackathon_telkom_university;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mapbox.maps.MapView;

public class Second_HomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    protected TextView getuser;
    protected String name;
    private static final String TAG = Second_HomeActivity.class.getSimpleName();
    protected final int LOCATION_PERMISSION_CODE = 101;
    private GoogleMap mMap;
    private CameraPosition cameraPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        getuser = findViewById(R.id.getUser);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Class_User user = snapshot.getValue(Class_User.class);
                getuser.setText("Hello, " + user.fullname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (isLocationPermissionGranted()){
            SupportMapFragment mapFragment = SupportMapFragment.newInstance();
//            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.map);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.map, mapFragment)
                    .commit();
            mapFragment.getMapAsync(this);
        }else{
            requestLocationPermission();
        }



        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.home);
        // Perform item selected listener
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
                    case R.id.home:
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(), Second_ProfileActivity.class));
                        overridePendingTransition(0,0);
                        onPause();
                        return true;
                }
                return false;
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        GoogleMapOptions options = new GoogleMapOptions();
        options.mapId(String.valueOf(R.string.map_id))
                .compassEnabled(true)
                .rotateGesturesEnabled(false)
                .tiltGesturesEnabled(false);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
            mMap.setTrafficEnabled(true);
        }


    }

    protected boolean isLocationPermissionGranted(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            return true;
        }else {
            return false;
        }
    }

    private void requestLocationPermission(){
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_CODE);
    }

    @Override
    protected void onPause() {
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        onMapReady(mMap);

        super.onPause();
    }
}