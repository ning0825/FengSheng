package com.tanhuan.fengsheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.tanhuan.fengsheng.MainActivity;
import com.tanhuan.fengsheng.R;
import com.tanhuan.fengsheng.adapter.MyListViewAdapter;
import com.tanhuan.fengsheng.db.WeatherDB;
import com.tanhuan.fengsheng.entity.Find;
import com.tanhuan.fengsheng.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.search_view) SearchView searchView;
    @BindView(R.id.lv_to_add) ListView listView;
    @BindView(R.id.lv_added) ListView listViewAdded;

    WeatherDB weatherDB;

    List<String> stringArray;

    List<String> cityArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        ButterKnife.bind(this);
        init();
    }

    public void init() {
        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        weatherDB = WeatherDB.getInstance(this);
        cityArray = weatherDB.getCity();
        stringArray = new ArrayList<>();

        final View headerView = getLayoutInflater().inflate(R.layout.header_city_list, null);
        listViewAdded.addHeaderView(headerView);
        listViewAdded.setHeaderDividersEnabled(false);
        listViewAdded.setAdapter(new MyListViewAdapter(this, R.layout.item_added_city, cityArray));

//        searchView initialize
        searchView.setIconified(false);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange (final String newText) {

                if (searchView.getQuery().toString().length() == 0) {
                    listViewAdded.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                    listViewAdded.setAdapter(new MyListViewAdapter(SearchActivity.this, R.layout.item_added_city, cityArray));

                } else {
                    listViewAdded.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    new Thread() {
                        @Override
                        public void run() {

                            try {
                                stringArray.clear();
                                List<Find.HeWeather6Bean.BasicBean> cityListBasic = HttpUtil.getCity(newText);
                                if (cityListBasic != null) {
                                    for (int i = 0; i < cityListBasic.size(); i++) {
                                        stringArray.add(cityListBasic.get(i).getLocation());
                                    }
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        listView.setAdapter(new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_list_item_1, stringArray));
                                    }
                                });
                            } catch (IOException e) {
                                Log.e("tag", e.getMessage());
                            }
                        }
                    }.start();
                }
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String cityName = stringArray.get(position);
                for (String s : cityArray) {
                    if (s.equals(cityName)) {
                        Toast.makeText(SearchActivity.this, "城市已存在", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                weatherDB.saveCity(cityName);
                Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                startActivity(intent);
                Toast.makeText(SearchActivity.this, "添加" + cityName + "成功", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
