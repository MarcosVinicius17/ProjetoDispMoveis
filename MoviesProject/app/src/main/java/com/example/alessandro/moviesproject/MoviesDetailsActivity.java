package com.example.alessandro.moviesproject;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MoviesDetailsActivity extends AppCompatActivity {
    private List<MoviesDetails> moviesDetails;
    public TextView movieDetailsTextView, movieNameTextView, iconTextView;
    public ImageView movieImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle bundle = getIntent().getExtras();
        String movieName = bundle.getString("movieName");
        String details = bundle.getString("details");
        String icon = bundle.getString("icon");

        movieImageView = findViewById(R.id.movieImageView);
        Bitmap figura = MoviesAdapter.getFiguras().get(icon);
        movieImageView.setImageBitmap(figura);

        movieDetailsTextView = findViewById(R.id.detailTextView);
        movieDetailsTextView.setText(details);

        movieNameTextView = findViewById(R.id.movieNameTextView);
        movieNameTextView.setText(movieName);

    }
}
