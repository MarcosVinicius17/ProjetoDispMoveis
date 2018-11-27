package com.example.alessandro.moviesproject;

import android.app.Activity;

public class Gender{
    public int genderId;
    public String genderName;

    public Gender(){

    }
    public Gender(int id, String genderName) {
        this.genderId = id; this.genderName = genderName;
    }

    public Gender(String genderName) {
        this.genderName = genderName;
    }
}

