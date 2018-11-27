package com.example.alessandro.moviesproject;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MoviesAdapter extends ArrayAdapter <Movies>{

    private static Map <String, Bitmap> figuras =
            new HashMap<>();

    public static Map<String, Bitmap> getFiguras() {
        return figuras;
    }

    public MoviesAdapter(@NonNull Context context, List<Movies> movies) {
        super(context, -1, movies);
    }

    private class ViewHolder {
        public ImageView movieImageView;
        public TextView movieName;
        public TextView popularity;
        public TextView like;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Context context = getContext();
        Movies filmeDaVez = getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater =
                    LayoutInflater.from(context);
            convertView = inflater.
                    inflate(R.layout.movie_item, parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.movieName =
                    convertView.findViewById(R.id.movieNameTextView);
            viewHolder.popularity =
                    convertView.findViewById(R.id.popularityTextView);
            viewHolder.like =
                    convertView.findViewById(R.id.likeTextView);
            viewHolder.movieImageView =
                    convertView.findViewById(R.id.movieImageView);
        }
        viewHolder = (ViewHolder) convertView.getTag();
        if (figuras.containsKey(filmeDaVez.iconName)){
            Bitmap figura = figuras.get(filmeDaVez.iconName);
            viewHolder.movieImageView.setImageBitmap(figura);
        }
        else{
            new DownloadImage(viewHolder.movieImageView, filmeDaVez.iconName).
                    execute(context.getString(R.string.image_download_URL, filmeDaVez.iconName));

        }

        viewHolder.movieName.setText
                (context.getString(R.string.movie, filmeDaVez.movieName));
        viewHolder.popularity.setText
                (context.getString(R.string.popularity, filmeDaVez.popularity));
        viewHolder.like.setText
                (context.getString(R.string.popularity, filmeDaVez.popularity));
        viewHolder.like.setText
                (context.getString(R.string.like, filmeDaVez.like));
        return convertView;
    }
    private class DownloadImage extends AsyncTask <String, Void, Bitmap>{
        private ImageView movieImageView;
        private String iconName;
        DownloadImage (ImageView movieImageView, String iconName){
            this.movieImageView = movieImageView;
            this.iconName = iconName;
        }
        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection =
                        (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                Bitmap figura = BitmapFactory.decodeStream(inputStream);
                return figura;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap figura) {
            movieImageView.setImageBitmap (figura);
            figuras.put(iconName,  figura);
        }
    }
}
