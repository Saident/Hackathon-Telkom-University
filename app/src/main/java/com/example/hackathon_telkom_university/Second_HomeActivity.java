package com.example.hackathon_telkom_university;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Second_HomeActivity extends AppCompatActivity implements OnMapReadyCallback {

    protected TextView getuser;
    protected Button direction, booknow;
    protected ImageView imageCafe, profile, remover;
    protected TextView cafe_name, about_us, address;
    protected Context context;

    protected RatingBar ratingBar;

    protected String name;
    private static final String TAG = Second_HomeActivity.class.getSimpleName();
    protected final int LOCATION_PERMISSION_CODE = 101;
    private GoogleMap mMap;
    protected ArrayList<Class_Coffee> listSatu = new ArrayList<>();
    protected DatabaseReference database;
    protected SlidingUpPanelLayout slidingUpPanelLayout;
    protected List<Marker> listMarker = new ArrayList<>();
    private CameraPosition cameraPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        direction = findViewById(R.id.direction);
        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Second_HomeActivity.this, "Direction not implemented yet", Toast.LENGTH_SHORT);
            }
        });

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
            CameraPosition cameraPosition = CameraPosition.fromLatLngZoom(latLng, 15);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 5);
            mMap.animateCamera(cameraUpdate);

            getDataLocation();

            mMap.setOnCameraMoveListener(() -> {
                for(Marker m:listMarker){
                    m.setVisible(mMap.getCameraPosition().zoom>10);
                }
            });

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(@NonNull Marker marker) {
                    showPanelUp(marker);
                    return true;
                }
            });
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

    private ArrayList<Class_Coffee> getCoffee(){
        for (Class_Coffee coffee : listSatu){
            getCoffee().add(coffee);
        }
        return getCoffee();
    }

    private void showPanelUp(Marker marker) {
        cafe_name = findViewById(R.id.cafe_name);
        about_us = findViewById(R.id.about_us);
        address = findViewById(R.id.address);
        imageCafe = findViewById(R.id.imageCafe);
        imageCafe.setScaleType(ImageView.ScaleType.CENTER_CROP);
        profile = findViewById(R.id.profile_pic_cafe);
        remover = findViewById(R.id.remover);

        slidingUpPanelLayout = findViewById(R.id.sliding_layout);
        slidingUpPanelLayout.setVisibility(View.VISIBLE);
        database = FirebaseDatabase.getInstance().getReference("Coffee");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSatu.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Class_Coffee post = dataSnapshot.getValue((Class_Coffee.class));
                    listSatu.add(post);
                    if (marker.getTitle().contains(post.getNameCoffee())){
                        getRating(post);
                        cafe_name.setText(post.getNameCoffee());
                        about_us.setText(post.getAbout_us());
                        address.setText(post.getAddress());
                        Glide.with(Second_HomeActivity.this).load(post.getImageref()).into(imageCafe);
                        Glide.with(Second_HomeActivity.this).load(post.getProfilePic()).into(profile);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        remover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                slidingUpPanelLayout.setVisibility(View.GONE);
            }
        });
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
                    BitmapDescriptor customMarker = BitmapDescriptorFactory.fromResource(R.drawable.marker);
                    Marker mMarker = mMap.addMarker(new MarkerOptions()
                            .position(listingPosition)
                            .title(post.getNameCoffee())
                            .snippet(post.getAddress())
                            .visible(false)
                            .icon(customMarker));
                    listMarker.add(mMarker);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getRating(Class_Coffee post){
        Float ratingTotal = Float.valueOf(post.getRate1()) +
                        Float.valueOf(post.getRate2()) +
                        Float.valueOf(post.getRate3()) +
                        Float.valueOf(post.getRate4()) +
                        Float.valueOf(post.getRate5());

        Float rating1 = Float.valueOf(post.getRate1());
        Float rating2 = Float.valueOf(post.getRate2())*2;
        Float rating3 = Float.valueOf(post.getRate3())*3;
        Float rating4 = Float.valueOf(post.getRate4())*4;
        Float rating5 = Float.valueOf(post.getRate5())*5;

        Float total = rating1 + rating2 + rating3 + rating4 + rating5;
        Float hasil = total/ratingTotal;
        ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(hasil);
    }

    @Override
    protected void onPause() {
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        super.onPause();
    }
}