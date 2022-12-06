package com.example.hackathon_telkom_university;

import java.util.ArrayList;

public class Class_Coffee {
    public String
            name =null,
            imageref =null,
            profilePic =null,
            address =null,
            lat =null,
            longt =null,
            about_us =null,
            noReserve =null,
            rate1 =null,
            rate2 =null,
            rate3 =null,
            rate4 =null,
            rate5 =null;

    public Class_Coffee(){}

    public Class_Coffee(String name, String imageref, String profilePic, String address, String lat, String longt, String about_us, String noReserve, String rate1, String rate2, String rate3, String rate4, String rate5) {
        this.name = name;
        this.imageref = imageref;
        this.profilePic = profilePic;
        this.address = address;
        this.lat = lat;
        this.longt = longt;
        this.about_us = about_us;
        this.noReserve = noReserve;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.rate3 = rate3;
        this.rate4 = rate4;
        this.rate5 = rate5;
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

    public String getName() {
        return name;
    }

    public String getProfilePic() {return profilePic;}

    public void setProfilePic(String profilePic) {this.profilePic = profilePic;}

    public String getAddress() {return address;}

    public void setAddress(String address) {this.address = address;}

    public String getLat() {return lat;}

    public void setLat(String lat) {this.lat = lat;}

    public String getLongt() {return longt;}

    public void setLongt(String longt) {this.longt = longt;}

    public String getAbout_us() {return about_us;}

    public void setAbout_us(String about_us) {this.about_us = about_us;}

    public String getNoReserve() {return noReserve;}

    public void setNoReserve(String noReserve) {this.noReserve = noReserve;}

    public String getRate1() {return rate1;}

    public void setRate1(String rate1) {this.rate1 = rate1;}

    public String getRate2() {return rate2;}

    public void setRate2(String rate2) {this.rate2 = rate2;}

    public String getRate3() {return rate3;}

    public void setRate3(String rate3) {this.rate3 = rate3;}

    public String getRate4() {return rate4;}

    public void setRate4(String rate4) {this.rate4 = rate4;}

    public String getRate5() {return rate5;}

    public void setRate5(String rate5) {this.rate5 = rate5;}
}
