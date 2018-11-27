package com.example.alessandro.moviesproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class GenderAdapter extends ArrayAdapter <Gender>{

    public GenderAdapter(@NonNull Context context, List<Gender> forecast) {
        super(context, -1, forecast);
    }

    private class ViewHolder {
        public TextView genderName;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Context context = getContext();
        Gender generoDaVez = getItem(position);
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater =
                    LayoutInflater.from(context);
            convertView = inflater.
                    inflate(R.layout.gender_item, parent, false);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.genderName = convertView.findViewById(R.id.genderNameTextView);
        }
        viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.genderName.setText(context.getString(R.string.gender_item, generoDaVez.genderName));
        return convertView;
    }
}

