package com.example.hackathon_telkom_university;

import java.util.ArrayList;

public class Class_User {

    public String fullname, username, email, phone;
    public ArrayList<Class_Favorit> classFavorit = new ArrayList<Class_Favorit>();

    public Class_User(){}
    public Class_User(String fullname, String username, String email) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
