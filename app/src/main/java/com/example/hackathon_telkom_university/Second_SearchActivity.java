package com.example.hackathon_telkom_university;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Second_SearchActivity extends AppCompatActivity {

    protected RecyclerView rec_recomm, rec_coffee, rec_working, rec_search;
    protected ArrayList<Class_Coffee>
            listSearch = new ArrayList<Class_Coffee>(),
            listSatu = new ArrayList<Class_Coffee>(),
            listDua = new ArrayList<Class_Coffee>(),
            listTiga = new ArrayList<Class_Coffee>();
    protected RecyclerView.LayoutManager RecyclerViewLayoutManager;
    protected Adapter_Coffee adapter_coffee;
    protected Adapter_Working adapter_working;
    protected Adapter_Search adapter_search;
    protected Adapter_Recomm adapter_recomm;
    protected androidx.appcompat.widget.SearchView searchView;
    protected DatabaseReference database;
    protected LinearLayoutManager HorizontalLayout, VerticalLayout;

    protected LinearLayout linearSearch;

    protected TextView tv_rec, tv_coffee, tv_working;

    View ChildView;
    int RecyclerViewItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        tv_rec = findViewById(R.id.tv_rec);
        tv_rec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Third_RecommendActivity.class));
                overridePendingTransition(0,0);
                onPause();
            }
        });
        tv_coffee = findViewById(R.id.tv_coffee);
        tv_coffee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Third_CoffeeActivity.class));
                overridePendingTransition(0,0);
                onPause();
            }
        });
        tv_working = findViewById(R.id.tv_working);
        tv_working.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), Third_CoWorkingActivity.class));
                overridePendingTransition(0,0);
                onPause();
            }
        });

        //initialize recView
        buildRecomm();
        buildCoffee();
        buildWorking();
        buildSearchView();
        buildSearch();
        botNav();

    }

    @Override
    protected void onPause() {
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        super.onPause();
    }

    private void botNav(){
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId())
                {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), Second_HomeActivity.class));
                        overridePendingTransition(0,0);
                        onPause();
                        return true;
                    case R.id.search:
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

    private void buildSearchView(){
        searchView = findViewById(R.id.search_bar);
        linearSearch = findViewById(R.id.linearSearch);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty()) {
                    linearSearch.setVisibility(View.GONE);
                }else{
                    linearSearch.setVisibility(View.VISIBLE);
                    filterList(newText);
                }
                return true;
            }
        });
    }

    private void filterList(String s) {
        linearSearch = findViewById(R.id.linearSearch);
        rec_search = findViewById(R.id.rec_search);
        List<Class_Coffee> filteredList = new ArrayList<>();
        for (Class_Coffee classCoffee : listSearch){
            if (classCoffee.getNameCoffee().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(classCoffee);
            }
        }
        if (filteredList.isEmpty()){
            filteredList.clear();
            adapter_search.setFilteredList(filteredList);
        }else {
            adapter_search.setFilteredList(filteredList);
        }
    }

    private void buildSearch(){
        rec_search = (RecyclerView)findViewById(R.id.rec_search);
        listSearch = new ArrayList<Class_Coffee>();
        database = FirebaseDatabase.getInstance().getReference("Coffee");
        adapter_search = new Adapter_Search(this,listSearch);
        rec_search.setAdapter(adapter_search);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSearch.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Class_Coffee post = dataSnapshot.getValue((Class_Coffee.class));
                    listSearch.add(post);
                }
                adapter_search.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        VerticalLayout = new LinearLayoutManager(Second_SearchActivity.this,
                LinearLayoutManager.VERTICAL, false);
        rec_search.setLayoutManager(VerticalLayout);
    }

    private void buildRecomm(){
        rec_recomm = (RecyclerView)findViewById(R.id.rec_recommend);
        listSatu = new ArrayList<Class_Coffee>();
        database = FirebaseDatabase.getInstance().getReference("Coffee");
        adapter_recomm = new Adapter_Recomm(this,listSatu);
        rec_recomm.setAdapter(adapter_recomm);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listSatu.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Class_Coffee post = dataSnapshot.getValue((Class_Coffee.class));
                    if (Integer.valueOf(post.getRate5()) > 600){
                        listSatu.add(post);
                    }
                }
                adapter_recomm.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        HorizontalLayout = new LinearLayoutManager(Second_SearchActivity.this,
                LinearLayoutManager.HORIZONTAL, false);
        rec_recomm.setLayoutManager(HorizontalLayout);
    }

    private void buildCoffee(){
        rec_coffee = (RecyclerView)findViewById(R.id.rec_coffee);
        listDua = new ArrayList<Class_Coffee>();
        database = FirebaseDatabase.getInstance().getReference("Coffee");
        adapter_coffee = new Adapter_Coffee(this,listDua);
        rec_coffee.setAdapter(adapter_coffee);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listDua.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Class_Coffee post = dataSnapshot.getValue((Class_Coffee.class));
                    if (post.getNameCoffee().toLowerCase().contains("Coffee".toLowerCase())){
                        listDua.add(post);
                    }
                }
                adapter_coffee.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        HorizontalLayout = new LinearLayoutManager(Second_SearchActivity.this,
                LinearLayoutManager.HORIZONTAL, false);
        rec_coffee.setLayoutManager(HorizontalLayout);
    }

    private void buildWorking(){
        rec_working = (RecyclerView)findViewById(R.id.rec_working);
        listTiga = new ArrayList<Class_Coffee>();
        database = FirebaseDatabase.getInstance().getReference("Coffee");
        adapter_working = new Adapter_Working(this,listTiga);
        rec_working.setAdapter(adapter_working);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listTiga.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Class_Coffee post = dataSnapshot.getValue((Class_Coffee.class));
                    if (post.getNameCoffee().toLowerCase().contains("Working".toLowerCase())){
                        listTiga.add(post);
                    }
                }
                adapter_working.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        HorizontalLayout = new LinearLayoutManager(Second_SearchActivity.this,
                LinearLayoutManager.HORIZONTAL, false);
        rec_working.setLayoutManager(HorizontalLayout);
    }

}