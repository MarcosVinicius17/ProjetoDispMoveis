package com.example.alessandro.moviesproject;

import android.app.Activity;

import java.util.List;

public class Movies{
    public int movieId;
    public String details, iconName, popularity, movieName, like;

    public Movies(MoviesActivity moviesActivity, List<Movies> movies) {
    }

    public String getIconName() {
        return iconName;
    }

    public Movies(int movieId, String movieName, String like, String popularity, String iconName, String details) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.popularity = popularity;
        this.like = like;
        this.iconName = iconName;
        this.details = details;

    }
}
