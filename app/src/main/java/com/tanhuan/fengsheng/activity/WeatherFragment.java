package com.tanhuan.fengsheng.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tanhuan.fengsheng.R;
import com.tanhuan.fengsheng.entity.AirNow;
import com.tanhuan.fengsheng.entity.NowWeather;
import com.tanhuan.fengsheng.entity.WeatherForecast;
import com.tanhuan.fengsheng.entity.WeatherMain;
import com.tanhuan.fengsheng.util.HttpUtil;
import com.tanhuan.fengsheng.util.OtherUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WeatherFragment extends Fragment {

    public WeatherFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        final String cityName = getArguments().getString("cityName");
        final View rootView = inflater.inflate(R.layout.weather_pager, container, false);

         final Handler mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                WeatherMain weatherMain = (WeatherMain)msg.obj;
                ViewHolder viewHolder = new ViewHolder(rootView);
                viewHolder.tmp.setText(weatherMain.getTmp());
                viewHolder.cond.setText(weatherMain.getCond());
                viewHolder.aq.setText(weatherMain.getAirQlty());
                viewHolder.date1.setText(weatherMain.getDate1());
                viewHolder.date2.setText(weatherMain.getDate2());
                viewHolder.date3.setText(weatherMain.getDate3());
                viewHolder.date4.setText(weatherMain.getDate4());
                viewHolder.date5.setText(weatherMain.getDate5());
                viewHolder.tmp1.setText(weatherMain.getTmp1());
                viewHolder.tmp2.setText(weatherMain.getTmp2());
                viewHolder.tmp3.setText(weatherMain.getTmp3());
                viewHolder.tmp4.setText(weatherMain.getTmp4());
                viewHolder.tmp5.setText(weatherMain.getTmp5());
                viewHolder.cond1.setText(weatherMain.getCond1());
                viewHolder.cond2.setText(weatherMain.getCond2());
                viewHolder.cond3.setText(weatherMain.getCond3());
                viewHolder.cond4.setText(weatherMain.getCond4());
                viewHolder.cond5.setText(weatherMain.getCond5());
            }
        };
        new Thread() {
            @Override
            public void run() {

                try {
                    NowWeather.HeWeather6Bean.NowBean nowWeather = HttpUtil.getNow(cityName);
                    String tmp = nowWeather.getTmp();
                    String cond = nowWeather.getCond_txt();

                    AirNow.HeWeather6Bean.AirNowCityBean airNowCityBean = HttpUtil.getAirNow(cityName);
                    String airQlty = airNowCityBean != null ? airNowCityBean.getQlty() : "null";

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

                    WeatherMain weatherMain = new WeatherMain();
                    weatherMain.setCityName(cityName);
                    weatherMain.setTmp(tmp);
                    weatherMain.setCond(cond);
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

                    Message message = new Message();
                    message.obj = weatherMain;
                    mHandler.sendMessage(message);


                } catch (IOException e) {
                    Log.d("dfs", e.getMessage());
                }

            }
        }.start();


        return rootView;
    }

    public static class ViewHolder {

        //上半部分控件
        @Nullable
        @BindView(R.id.city_name)
        public TextView cityName;
        @BindView(R.id.tmp)
        public TextView tmp;
        @BindView(R.id.cond)
        public TextView cond;
        //空气质量
        @BindView(R.id.qlty)
        public TextView aq;

        //下半部分控件
        @BindView(R.id.date0)
        public TextView date1;
        @BindView(R.id.date1)
        public TextView date2;
        @BindView(R.id.date2)
        public TextView date3;
        @BindView(R.id.date3)
        public TextView date4;
        @BindView(R.id.date4)
        public TextView date5;
        @BindView(R.id.temp0)
        public TextView tmp1;
        @BindView(R.id.temp1)
        public TextView tmp2;
        @BindView(R.id.temp2)
        public TextView tmp3;
        @BindView(R.id.temp3)
        public TextView tmp4;
        @BindView(R.id.temp4)
        public TextView tmp5;
        @BindView(R.id.wea0)
        public TextView cond1;
        @BindView(R.id.wea1)
        public TextView cond2;
        @BindView(R.id.wea2)
        public TextView cond3;
        @BindView(R.id.wea3)
        public TextView cond4;
        @BindView(R.id.wea4)
        public TextView cond5;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
