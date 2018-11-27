package com.example.alessandro.moviesproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class MainActivity extends AppCompatActivity {
    private GenderAdapter adapter;
    private List<Gender> generos;
    private ListView genderListView;
    public TextView genderNameTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        generos = new ArrayList<>();

        //generos
        genderNameTextView = findViewById(R.id.genderNameTextView);
        adapter = new GenderAdapter(this, generos);
        genderListView = findViewById(R.id.genderListView);
        genderListView.setAdapter(adapter);

        String endereco =
                getString(R.string.gender_web_service_url,
                        getString(R.string.api_key),
                        getString(R.string.language));
        new ObtemGeneros().execute(endereco);

        genderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Gender gender = (Gender) genderListView.getItemAtPosition(position);
                int genderId = gender.genderId;
                Intent intent = new Intent(com.example.alessandro.moviesproject.MainActivity.this, com.example.alessandro.moviesproject.MoviesActivity.class);

                intent.putExtra("genderId", genderId);
                startActivity(intent);


            }
        });

    }

    private class ObtemGeneros extends
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
                generos.clear();
                JSONObject json = new JSONObject(jsonS);
                JSONArray list = json.getJSONArray("genres");
                for (int i = 0; i < list.length(); i++){
                    JSONObject gender = list.getJSONObject(i);
                    String nGenero = gender.getString("name");
                    int id = gender.getInt("id");
                    Gender g = new Gender(id, nGenero);
                    generos.add(g);
                }
                adapter.notifyDataSetChanged();

            }
            catch (JSONException e){
                e.printStackTrace();
            }


        }
    }

}
