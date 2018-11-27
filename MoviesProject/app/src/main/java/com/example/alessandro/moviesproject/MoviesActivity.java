package com.example.alessandro.moviesproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {
    public TextView movieName, like, popularity, details;
    private List<Movies> movies;
    private MoviesAdapter movieAdapter;
    private ListView movieListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        movies = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        int genderId = bundle.getInt(    "genderId");
        String idG = Integer.toString(genderId);
        movieName = findViewById(R.id.movieNameTextView);
        popularity = findViewById(R.id.popularityTextView);
        like = findViewById(R.id.likeTextView);
        details = findViewById(R.id.detailTextView);
        movieAdapter = new MoviesAdapter(this, movies);
        movieListView= findViewById(R.id.movieListView);
        movieListView.setAdapter(movieAdapter);
        String endereco = getString(R.string.movie_web_service_url, getString(R.string.api_key), getString(R.string.language), idG);
        new ObtemFilmes().execute(endereco);
        movieListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movies movie = (Movies) movieListView.getItemAtPosition(position);
                String details = movie.details;
                String icon = movie.iconName;
                String movieName = movie.movieName;
                Intent intent = new Intent(com.example.alessandro.moviesproject.MoviesActivity.this, com.example.alessandro.moviesproject.MoviesDetailsActivity.class);
                intent.putExtra("movieName", movieName);
                intent.putExtra("details", details);
                intent.putExtra("icon", icon);
                startActivity(intent);
            }
        });

    }

    private class ObtemFilmes extends
            AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... enderecos) {
            try{
                URL url = new URL(enderecos[0]);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader =
                        new BufferedReader(new InputStreamReader(inputStream));
                String linha = null;
                String json = "";
                while ((linha = reader.readLine()) != null){
                    json += linha;
                }
                return json;
            }
            catch (MalformedURLException e){
                e.printStackTrace();

            }
            catch (IOException e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String jsonS) {
            try{
                movies.clear();
                JSONObject json = new JSONObject(jsonS);
                JSONArray list = json.getJSONArray("results");
                for (int i = 0; i < list.length(); i++){
                    JSONObject filme = list.getJSONObject(i);
                    String nFilme= filme.getString("title");
                    String popul=filme.getString("popularity");
                    String voto = filme.getString("vote_average");
                    String icon = filme.getString("poster_path");
                    String detalhes = filme.getString("overview");
                    int id = filme.getInt("id");
                    Movies m = new Movies(id, nFilme, popul, voto, icon, detalhes);
                    movies.add(m);
                }
                movieAdapter.notifyDataSetChanged();

            }
            catch (JSONException e){
                e.printStackTrace();
            }


        }
    }
}
