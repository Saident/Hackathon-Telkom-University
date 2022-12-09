package com.example.hackathon_telkom_university;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UFourth_Favorite extends AppCompatActivity {
    protected ArrayList<Class_Coffee> listSearch = new ArrayList<Class_Coffee>();
    protected RecyclerView rec_fav;
    protected LinearLayout linearSearch;
    protected Adapter_Individual adapter_search;
    protected androidx.appcompat.widget.SearchView searchView;
    protected DatabaseReference database;
    protected LinearLayoutManager VerticalLayout;

    protected ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ufourth_favorite);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        buildSearch();
//        buildSearchView();
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
                }else{
                    filterList(newText);
                }
                return true;
            }
        });
    }

    private void filterList(String s) {
        linearSearch = findViewById(R.id.linearSearch);
        rec_fav = findViewById(R.id.rec_fav);
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
        rec_fav = (RecyclerView)findViewById(R.id.rec_fav);
        listSearch = new ArrayList<Class_Coffee>();
        database = FirebaseDatabase.getInstance().getReference().child("Users")
                .child(FirebaseAuth.getInstance().getUid())
                .child("Favorite");
        adapter_search = new Adapter_Individual(this,listSearch);
        rec_fav.setAdapter(adapter_search);
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
        VerticalLayout = new LinearLayoutManager(UFourth_Favorite.this,
                LinearLayoutManager.VERTICAL, false);
        rec_fav.setLayoutManager(VerticalLayout);
    }
}