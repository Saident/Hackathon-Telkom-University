package com.example.hackathon_telkom_university;

import java.util.ArrayList;

public class Class_Coffee {
    public String name, imageref, location;

    public Class_Coffee(){}
    public Class_Coffee(String name, String imageref, String location) {
        this.name = name;
        this.imageref = imageref;
        this.location = location;
    }

    public String getNameCoffee() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageref() {
        return imageref;
    }

    public void setImageref(String imageref) {
        this.imageref = imageref;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
