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
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class Third_CoWorkingActivity extends AppCompatActivity {

    protected ArrayList<Class_Coffee> listSearch = new ArrayList<Class_Coffee>();
    protected RecyclerView rec_search;
    protected LinearLayout linearSearch;
    protected Adapter_Search adapter_search;
    protected androidx.appcompat.widget.SearchView searchView;
    protected DatabaseReference database;
    protected LinearLayoutManager VerticalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_recommend);
        buildSearch();
        buildSearchView();
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
        rec_search = findViewById(R.id.rec_search);
        List<Class_Coffee> filteredList = new ArrayList<>();
        for (Class_Coffee classCoffee : listSearch){
            if (classCoffee.getNameCoffee().toLowerCase().contains(s.toLowerCase())){
                filteredList.add(classCoffee);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show();
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
                    if (post.getNameCoffee().toLowerCase().contains("Working".toLowerCase())){
                        listSearch.add(post);
                    }
                }
                adapter_search.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        VerticalLayout = new LinearLayoutManager(Third_CoWorkingActivity.this,
                LinearLayoutManager.VERTICAL, false);
        rec_search.setLayoutManager(VerticalLayout);
    }
}