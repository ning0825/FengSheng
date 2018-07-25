package com.tanhuan.fengsheng;

import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.tanhuan.fengsheng.activity.EmptyCityActivity;
import com.tanhuan.fengsheng.activity.SettingActivity;
import com.tanhuan.fengsheng.activity.WeatherFragment;
import com.tanhuan.fengsheng.adapter.MyFragmentPagerAdapter;
import com.tanhuan.fengsheng.db.WeatherDB;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity{

    @BindView(R.id.viewpager)
    ViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.setting)
    ImageButton btSetting;
    @BindView(R.id.city_name)
    TextView cityName;
    MyFragmentPagerAdapter myFragmentPagerAdapter;
    List<Fragment> fragments;
    List<String> city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        init();

        btSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

    }

    class MyPagerChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            cityName.setText(city.get(viewPager.getCurrentItem()));

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    public void init() {

        //获取WeatherDB实例
        WeatherDB weatherDB = WeatherDB.getInstance(this);
        //fragment列表
        fragments = new ArrayList<>();
        //数据库中城市列表
        city = weatherDB.getCity();
        if (city.size() == 0) {
            Intent intent = new Intent(this, EmptyCityActivity.class);
            startActivity(intent);
        } else {
            for (String cityName : city) {
                //根据数据库中城市添加fragment
                WeatherFragment weatherFragment = new WeatherFragment();
                Bundle bundle = new Bundle();
                bundle.putString("cityName", cityName);
                weatherFragment.setArguments(bundle);
                fragments.add(weatherFragment);
            }
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(fragmentManager, fragments);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new MyPagerChangeListener());
        cityName.setText(city.get(viewPager.getCurrentItem()));
    }




}
