package com.example.hackathon_telkom_university;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

public class Second_HomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    protected TextView getuser;
    protected String name;
    private static final String TAG = Second_HomeActivity.class.getSimpleName();
    protected final int LOCATION_PERMISSION_CODE = 101;
    private GoogleMap mMap;
    protected ArrayList<Class_Coffee> listSatu = new ArrayList<>();
    protected DatabaseReference database;
    protected SlidingUpPanelLayout slidingUpPanelLayout;
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
        botNav();
    }

    private void botNav(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
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
//        GoogleMapOptions options = new GoogleMapOptions();
//        options.mapId(String.valueOf(R.string.map_id))
//                .compassEnabled(true)
//                .rotateGesturesEnabled(false)
//                .tiltGesturesEnabled(false);
        try {
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            double lat = -7.977050292059499, longt = 112.63405731852441;
            LatLng latLng = new LatLng(lat, longt);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLng(latLng);
//            CameraUpdate cameraUpdate2 = CameraUpdateFactory.zoomBy(10);
            mMap.animateCamera(cameraUpdate);
            getDataLocation();

            if (mMap != null){
                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        marker.getTitle();
                        return true;
                    }
                });
            }
//            mMap.animateCamera(cameraUpdate2);
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

    private void showBottomSheetDialog() {
        slidingUpPanelLayout = findViewById(R.id.sliding_layout);
    }

    private void requestLocationPermission(){
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_PERMISSION_CODE);
    }

    private void getDataLocation(){
        database = FirebaseDatabase.getInstance().getReference("Coffee");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSatu.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Class_Coffee post = dataSnapshot.getValue((Class_Coffee.class));
                    listSatu.add(post);
                    LatLng listingPosition = new LatLng(Double.valueOf(post.getLat()), Double.valueOf(post.getLongt()));
                    Marker mapMarker = mMap.addMarker(new MarkerOptions()
                            .position(listingPosition)
                            .title(post.getName())
                            .snippet(post.getAddress()));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    protected void onPause() {
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        super.onPause();
    }
}