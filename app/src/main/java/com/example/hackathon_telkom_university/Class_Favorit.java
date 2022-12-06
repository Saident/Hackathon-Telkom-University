package com.example.hackathon_telkom_university;

import java.util.ArrayList;

public class Class_Favorit {
    protected ArrayList<Class_Coffee> fav = new ArrayList<>();

    public Class_Favorit(ArrayList<Class_Coffee> fav) {
        this.fav = fav;
    }

    public ArrayList<Class_Coffee> getFav() {
        return fav;
    }

    public void setFav(ArrayList<Class_Coffee> fav) {
        this.fav = fav;
    }
}
