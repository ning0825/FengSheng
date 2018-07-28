package com.tanhuan.fengsheng.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tanhuan.fengsheng.MainActivity;
import com.tanhuan.fengsheng.R;
import com.tanhuan.fengsheng.db.WeatherDB;

import java.util.ArrayList;
import java.util.List;

public class MyListViewAdapter extends ArrayAdapter {

    int resourceId;
    List<String> cityArray;
    WeatherDB weatherDB;

    public MyListViewAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        resourceId = resource;
        cityArray = objects;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        weatherDB = WeatherDB.getInstance(getContext());
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        TextView textView = view.findViewById(R.id.tv_city_name);
        ImageButton imageButton = view.findViewById(R.id.ib_del_city);
        String name = cityArray.get(position);
        textView.setText(name);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cityName = getItem(position);
                weatherDB.deleteCity(cityName);
                Toast.makeText(getContext(), "删除" + cityName + "成功", Toast.LENGTH_SHORT).show();
                remove(getItem(position));
                Intent bcrIntent = new Intent("CITY_CHANGE");
                getContext().sendBroadcast(bcrIntent);
                if (getCount() == 0) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(intent);
                }

            }
        });

        return view;
    }

    @Override
    public int getCount() {
        return cityArray.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return cityArray.get(position);
    }
}
