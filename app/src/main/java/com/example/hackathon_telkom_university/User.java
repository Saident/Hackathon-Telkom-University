package com.example.hackathon_telkom_university;

import java.util.ArrayList;

public class User {

    public String fullname, username, email;
    public ArrayList<Favorit> favorit = new ArrayList<Favorit>();

    public User(){}
    public User(String fullname, String username, String email) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
    }
}
