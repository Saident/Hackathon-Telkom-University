package com.example.hackathon_telkom_university;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class Second_SearchActivity extends AppCompatActivity {

    protected RecyclerView rec_recomm, rec_coffee, rec_working;
    protected ArrayList<String> list1, list2, list3;
    protected RecyclerView.LayoutManager RecyclerViewLayoutManager;
    protected Adapter_Reccomend adapter_reccomend;
    protected Adapter_Coffee adapter_coffee;
    protected  Adapter_Working adapter_working;
    LinearLayoutManager HorizontalLayout;
    View ChildView;
    int RecyclerViewItemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //initialize recView
        buildRecommend();
        buildCoffee();
        buildWorking();

        // Initialize and assign variable
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);

        // Set Home selected
        bottomNavigationView.setSelectedItemId(R.id.search);

        // Perform item selected listener
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


    @Override
    protected void onPause() {
        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.search);
        super.onPause();
    }

    private void buildRecommend(){
        rec_recomm = (RecyclerView)findViewById(R.id.rec_recommend);
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        rec_recomm.setLayoutManager(RecyclerViewLayoutManager);
        addItemsRecommend();
        adapter_reccomend = new Adapter_Reccomend(list1);
        HorizontalLayout = new LinearLayoutManager(Second_SearchActivity.this,
                LinearLayoutManager.HORIZONTAL, false);
        rec_recomm.setLayoutManager(HorizontalLayout);
        rec_recomm.setAdapter(adapter_reccomend);
    }

    private void buildCoffee(){
        rec_coffee = (RecyclerView)findViewById(R.id.rec_coffee);
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        rec_coffee.setLayoutManager(RecyclerViewLayoutManager);
        addItemsCoffee();
        adapter_coffee = new Adapter_Coffee(list2);
        HorizontalLayout = new LinearLayoutManager(Second_SearchActivity.this,
                LinearLayoutManager.HORIZONTAL, false);
        rec_coffee.setLayoutManager(HorizontalLayout);
        rec_coffee.setAdapter(adapter_coffee);
    }

    private void buildWorking(){
        rec_working = (RecyclerView)findViewById(R.id.rec_working);
        RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        rec_working.setLayoutManager(RecyclerViewLayoutManager);
        addItemsWorking();
        adapter_working = new Adapter_Working(list3);
        HorizontalLayout = new LinearLayoutManager(Second_SearchActivity.this,
                LinearLayoutManager.HORIZONTAL, false);
        rec_working.setLayoutManager(HorizontalLayout);
        rec_working.setAdapter(adapter_working);
    }

    private void addItemsRecommend() {
        list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
    }

    private void addItemsCoffee() {
        list2 = new ArrayList<>();
        list2.add("1");
        list2.add("2");
        list2.add("3");
        list2.add("4");
    }

    private void addItemsWorking() {
        list3 = new ArrayList<>();
        list3.add("1");
        list3.add("2");
        list3.add("3");
        list3.add("4");
    }

}