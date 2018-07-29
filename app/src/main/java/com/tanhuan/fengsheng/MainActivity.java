package com.tanhuan.fengsheng;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tanhuan.fengsheng.activity.EmptyCityActivity;
import com.tanhuan.fengsheng.activity.SettingActivity;
import com.tanhuan.fengsheng.activity.SplashActivity;
import com.tanhuan.fengsheng.activity.WeatherFragment;
import com.tanhuan.fengsheng.adapter.MyFragmentPagerAdapter;
import com.tanhuan.fengsheng.bean.LifeStyle;
import com.tanhuan.fengsheng.cview.MySwipeRefreshLayout;
import com.tanhuan.fengsheng.cview.MyViewPager;
import com.tanhuan.fengsheng.db.WeatherDB;
import com.tanhuan.fengsheng.bean.AirNow;
import com.tanhuan.fengsheng.bean.NowWeather;
import com.tanhuan.fengsheng.bean.WeatherForecast;
import com.tanhuan.fengsheng.entity.WeatherMain;
import com.tanhuan.fengsheng.util.HttpUtil;
import com.tanhuan.fengsheng.util.OtherUtil;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tanhuan.fengsheng.util.HttpUtil.getWM;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    MyViewPager viewPager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.setting)
    ImageButton btSetting;
    @BindView(R.id.city_name)
    TextView cityName;
    @BindView(R.id.srl)
    MySwipeRefreshLayout srl;

    MyFragmentPagerAdapter myFragmentPagerAdapter;
    List<Fragment> fragments;
    List<String> city;
    Mbcr mbcr;
    IntentFilter intentFilter = new IntentFilter("CITY_CHANGE");
    List<WeatherMain> weatherMains;


//    Handler mHandler = new Handler() {
//
//
//        @Override
//        public void handleMessage(Message msg) {
//
//            //fragment列表
//            fragments = new ArrayList<>();
//
//            List<WeatherMain> weatherMains = (List<WeatherMain>) msg.obj;
//
//            for (WeatherMain wm : weatherMains) {
//                WeatherFragment weatherFragment = new WeatherFragment();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("weather_main", wm);
//                weatherFragment.setArguments(bundle);
//                fragments.add(weatherFragment);
//            }
//
//            FragmentManager fragmentManager = getSupportFragmentManager();
//            Log.e("------fm", String.valueOf(fragments.size()));
//            Log.e("______wm", String.valueOf(weatherMains.size()));
//            myFragmentPagerAdapter = new MyFragmentPagerAdapter(fragmentManager, fragments);
//            viewPager.setAdapter(myFragmentPagerAdapter);
//            viewPager.addOnPageChangeListener(new MyPagerChangeListener());
//            cityName.setText(city.get(viewPager.getCurrentItem()));
//
//        }
//
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        btSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        //动态注册广播接收器
        mbcr = new Mbcr();
        registerReceiver(mbcr, intentFilter);

        fragments = new ArrayList<>();

        //获取WeatherDB实例
        WeatherDB weatherDB = WeatherDB.getInstance(MainActivity.this);
        //数据库中城市列表
        city = weatherDB.getCity();
        if (city.size() == 0) {
            startActivity(new Intent(this, EmptyCityActivity.class));
        }
        if (getIntent().getSerializableExtra("weather_mains") != null) {
            weatherMains = (List<WeatherMain>) getIntent().getSerializableExtra("weather_mains");
            init(weatherMains);
        } else if (getIntent().getBooleanExtra("noNetwork", false)) {
            showDialog();
        }


        //SwipeRefreshLayout 刷新监听
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (fragments.size() == 0) {
                    new Thread() {
                        @Override
                        public void run() {
                            for (final String cityName : city) {
                                weatherMains = new ArrayList<>();
                                weatherMains.add(HttpUtil.getWM(cityName));
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {

                                    init(weatherMains);
                                    srl.setRefreshing(false);
                                }
                            });
                        }
                    }.start();
                } else {
                    //执行刷新操作
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            if (OtherUtil.hasNetWork(MainActivity.this)) {
                                int currentItem = viewPager.getCurrentItem();
                                Fragment currentFragment = fragments.get(currentItem);
                                String currentCity = cityName.getText().toString();
                                final WeatherMain weatherMain = getWM(currentCity);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("weather_main", weatherMain);
                                currentFragment.setArguments(bundle);
                                currentFragment.onCreate(bundle);

                                srl.setRefreshing(false);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(MainActivity.this, "刷新成功, 时间：" + weatherMain.getUdTime(), Toast.LENGTH_SHORT).show();

                                    }
                                });
                            } else {
                                showDialog();
                            }

                        }
                    }).start();
                }


            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        unregisterReceiver(mbcr);
        if (!mbcr.isOrderedBroadcast()) {
            registerReceiver(mbcr, intentFilter);
        }
    }


    /**
     * 监听viewpager change 事件更改toolbar中城市名称
     */
    class MyPagerChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            int i = viewPager.getCurrentItem();
            cityName.setText(city.get(i));

        }
    }

    //接收数据库数据变化
    public class Mbcr extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e("--------->>>citychange", "dsjla;f");
            if (OtherUtil.hasNetWork(MainActivity.this)) {

                final List<WeatherMain> weatherMains = new ArrayList<>();
                //获取WeatherDB实例
                WeatherDB weatherDB = WeatherDB.getInstance(MainActivity.this);
                //数据库中城市列表
                city = weatherDB.getCity();

                if (city.size() == 0) {
                    Intent intent1 = new Intent(MainActivity.this, EmptyCityActivity.class);
                    startActivity(intent1);
                    finish();
                } else {
                    new Thread() {
                        @Override
                        public void run() {
                            for (final String cityName : city) {
                                weatherMains.add(HttpUtil.getWM(cityName));
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    init(weatherMains);

                                }
                            });
                        }
                    }.start();
                }
            } else {
                showDialog();
            }
        }
    }


    public void init(List<WeatherMain> weatherMains) {
        fragments.clear();
        for (WeatherMain wm : weatherMains) {
            WeatherFragment weatherFragment = new WeatherFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("weather_main", wm);
            weatherFragment.setArguments(bundle);
            fragments.add(weatherFragment);
            Log.e("-------->>>>>", String.valueOf(fragments.size()));
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        myFragmentPagerAdapter = new MyFragmentPagerAdapter(fragmentManager, fragments);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.addOnPageChangeListener(new MyPagerChangeListener());
        cityName.setText(city.get(viewPager.getCurrentItem()));
    }

    public void showDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("恕我直言，没网吧好像？去设置设置？")
                .setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                    }
                })
                .setNegativeButton("算了", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }
}






