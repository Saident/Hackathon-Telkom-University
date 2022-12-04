package com.example.hackathon_telkom_university;

import java.util.ArrayList;

public class Class_User {

    public String fullname, username, email;
    public ArrayList<Class_Favorit> classFavorit = new ArrayList<Class_Favorit>();

    public Class_User(){}
    public Class_User(String fullname, String username, String email) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
    }
}
