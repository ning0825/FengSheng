package com.tanhuan.fengsheng;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
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
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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


    Handler mHandler = new Handler() {


        @Override
        public void handleMessage(Message msg) {

            //fragment列表
            fragments = new ArrayList<>();

            List<WeatherMain> weatherMains = (List<WeatherMain>) msg.obj;

            for (WeatherMain wm : weatherMains) {
                WeatherFragment weatherFragment = new WeatherFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("weather_main", wm);
                weatherFragment.setArguments(bundle);
                fragments.add(weatherFragment);
            }

            FragmentManager fragmentManager = getSupportFragmentManager();
            Log.e("------fm", String.valueOf(fragments.size()));
            Log.e("______wm", String.valueOf(weatherMains.size()));
            myFragmentPagerAdapter = new MyFragmentPagerAdapter(fragmentManager, fragments);
            viewPager.setAdapter(myFragmentPagerAdapter);
            viewPager.addOnPageChangeListener(new MyPagerChangeListener());
            cityName.setText(city.get(viewPager.getCurrentItem()));

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        toolbar.setTitle(" ");
        setSupportActionBar(toolbar);

        //动态注册广播接收器

        mbcr = new Mbcr();
        registerReceiver(mbcr, intentFilter);

        init();

        btSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        //SwipeRefreshLayout 刷新监听
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //执行刷新操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        int currentItem = viewPager.getCurrentItem();
                        Fragment currentFragment = fragments.get(currentItem);
                        String currentCity = city.get(currentItem);
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

                    }
                }).start();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mbcr.isOrderedBroadcast()) {
            registerReceiver(mbcr, intentFilter);
        }
    }

    public void init() {

        final List<WeatherMain> weatherMains = new ArrayList<>();

        //获取WeatherDB实例
        WeatherDB weatherDB = WeatherDB.getInstance(this);

        //数据库中城市列表
        city = weatherDB.getCity();

        if (city.size() == 0) {
            Intent intent = new Intent(this, EmptyCityActivity.class);
            startActivity(intent);
        } else {

            new Thread() {
                @Override
                public void run() {
                    for (final String cityName : city) {

                        weatherMains.add(getWM(cityName));

                    }
                    Message message = new Message();
                    message.obj = weatherMains;
                    mHandler.sendMessage(message);
                }
            }.start();
        }

        unregisterReceiver(mbcr);

    }


    //获取数据并返回 WeatherMain 对象
    public WeatherMain getWM(String cityName) {

        WeatherMain weatherMain = new WeatherMain();

        try {
            NowWeather.HeWeather6Bean.NowBean nowWeather = HttpUtil.getNowBean(cityName);
            String tmp = nowWeather.getTmp();
            String cond = nowWeather.getCond_txt();
            String udTime = HttpUtil.getNowTime();

            String airQlty = "";
            boolean hasDate = true;
            AirNow.HeWeather6Bean.AirNowCityBean airNowCityBean = HttpUtil.getAirNow(cityName);
            if (airNowCityBean != null) {
                airQlty = airNowCityBean.getQlty();
            } else {
                airQlty = "";
                hasDate = false;
            }

            List<WeatherForecast.HeWeather6Bean.DailyForecastBean> dailyForecastBeans = HttpUtil.getForecast(cityName);
            WeatherForecast.HeWeather6Bean.DailyForecastBean dailyForecastBean1 = dailyForecastBeans.get(0);
            String date1 = OtherUtil.toDay(dailyForecastBean1.getDate());
            String tmp1 = dailyForecastBean1.getTmp_max();
            String cond1 = dailyForecastBean1.getCond_txt_d();

            WeatherForecast.HeWeather6Bean.DailyForecastBean dailyForecastBean2 = dailyForecastBeans.get(1);
            String date2 = OtherUtil.toDay(dailyForecastBean2.getDate());
            String tmp2 = dailyForecastBean2.getTmp_max();
            String cond2 = dailyForecastBean2.getCond_txt_d();

            WeatherForecast.HeWeather6Bean.DailyForecastBean dailyForecastBean3 = dailyForecastBeans.get(2);
            String date3 = OtherUtil.toDay(dailyForecastBean3.getDate());
            String tmp3 = dailyForecastBean3.getTmp_max();
            String cond3 = dailyForecastBean3.getCond_txt_d();

            WeatherForecast.HeWeather6Bean.DailyForecastBean dailyForecastBean4 = dailyForecastBeans.get(3);
            String date4 = OtherUtil.toDay(dailyForecastBean4.getDate());
            String tmp4 = dailyForecastBean4.getTmp_max();
            String cond4 = dailyForecastBean4.getCond_txt_d();

            WeatherForecast.HeWeather6Bean.DailyForecastBean dailyForecastBean5 = dailyForecastBeans.get(4);
            String date5 = OtherUtil.toDay(dailyForecastBean5.getDate());
            String tmp5 = dailyForecastBean5.getTmp_max();
            String cond5 = dailyForecastBean5.getCond_txt_d();

            //set weatherMain 对象
            weatherMain.setCityName(cityName);
            weatherMain.setTmp(tmp);
            weatherMain.setCond(cond);
            weatherMain.setUdTime(udTime);
            weatherMain.setAirQlty(airQlty);

            weatherMain.setDate1(date1);
            weatherMain.setTmp1(tmp1);
            weatherMain.setCond1(cond1);

            weatherMain.setDate2(date2);
            weatherMain.setTmp2(tmp2);
            weatherMain.setCond2(cond2);

            weatherMain.setDate3(date3);
            weatherMain.setTmp3(tmp3);
            weatherMain.setCond3(cond3);

            weatherMain.setDate4(date4);
            weatherMain.setTmp4(tmp4);
            weatherMain.setCond4(cond4);

            weatherMain.setDate5(date5);
            weatherMain.setTmp5(tmp5);
            weatherMain.setCond5(cond5);

            //风况
            String windDir = nowWeather.getWind_dir();
            String windSc = nowWeather.getWind_sc();
            String windSpd = nowWeather.getWind_spd();

            //空气质量
            String aqi = "";
            String pm10 = "";
            String pm25 = "";
            if (hasDate) {
                aqi = airNowCityBean.getAqi();
                pm10 = airNowCityBean.getPm10();
                pm25 = airNowCityBean.getPm25();
            }


            //生活指数
            List<LifeStyle.HeWeather6Bean.LifestyleBean> lifestyleBeans = HttpUtil.getLifeStyle(cityName);

            weatherMain.setWindDir(windDir);
            weatherMain.setWindSc(windSc);
            weatherMain.setWindSpd(windSpd);
            weatherMain.setHasDate(hasDate);
            weatherMain.setAqi(aqi);
            weatherMain.setPm10(pm10);
            weatherMain.setPm25(pm25);
            weatherMain.setLifestyleBeans(lifestyleBeans);

        } catch (IOException e) {
            Log.d("dfs", e.getMessage());
        }
        return weatherMain;
    }


    /**
     * 监听viewpager change 事件更改toolbar中城市名称
     */
    class MyPagerChangeListener extends ViewPager.SimpleOnPageChangeListener {
        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            cityName.setText(city.get(viewPager.getCurrentItem()));

        }
    }

    //接收数据库数据变化
    class Mbcr extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            init();

        }
    }

    //上划手势识别进入天气详情
    private float startX;
    private float startY;
    private float endX;
    private float endY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("---------main", "onTouchEvent: down");
                startX = MotionEvent.obtain(event).getX();
                startY = MotionEvent.obtain(event).getY();
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("----------main", "onTouchEvent: move");
                endX = event.getX();
                endY = event.getY();

                float diffX = Math.abs(endX - startX);
                float diffY = Math.abs(endY - startY);

                if (diffY > diffX) {
                    Log.e("------....>>>", "sanghua shoushi ");
                }

                break;
            case MotionEvent.ACTION_UP:
                Log.e("---------main", "onTouchEvent: up");

                break;
        }
        return super.onTouchEvent(event);
    }

}






