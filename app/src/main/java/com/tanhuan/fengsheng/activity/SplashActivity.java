package com.tanhuan.fengsheng.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.tanhuan.fengsheng.MainActivity;
import com.tanhuan.fengsheng.R;
import com.tanhuan.fengsheng.db.WeatherDB;
import com.tanhuan.fengsheng.entity.WeatherMain;
import com.tanhuan.fengsheng.util.HttpUtil;
import com.tanhuan.fengsheng.util.OtherUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (OtherUtil.hasNetWork(this)) {
            final List<WeatherMain> weatherMains = new ArrayList<>();
            //获取WeatherDB实例
            WeatherDB weatherDB = WeatherDB.getInstance(this);
            //数据库中城市列表
            final List<String> city = weatherDB.getCity();

            if (city.size() == 0) {
                new Handler(new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        return false;
                    }
                }).sendEmptyMessageDelayed(0, 1000);

            } else {
                new Thread() {
                    @Override
                    public void run() {
                        for (final String cityName : city) {
                            weatherMains.add(HttpUtil.getWM(cityName));
                        }
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.putExtra("weather_mains", (Serializable)weatherMains);
                        startActivity(intent);
                        finish();
                    }
                }.start();
            }
        } else {
            new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.putExtra("noNetwork", true);
                    startActivity(intent);
                    finish();
                    return false;
                }
            }).sendEmptyMessageDelayed(0, 800);
        }




    }



}
