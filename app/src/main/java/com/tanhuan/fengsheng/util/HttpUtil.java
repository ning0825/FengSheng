package com.tanhuan.fengsheng.util;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.tanhuan.fengsheng.bean.AirNow;
import com.tanhuan.fengsheng.bean.Find;
import com.tanhuan.fengsheng.bean.LifeStyle;
import com.tanhuan.fengsheng.bean.NowWeather;
import com.tanhuan.fengsheng.bean.WeatherForecast;
import com.tanhuan.fengsheng.entity.WeatherMain;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.ContentValues.TAG;

public class HttpUtil {

    private static OkHttpClient client = new OkHttpClient();
    private static Gson gson = new Gson();

    //根据URL获取数据
    public static String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String responseString = response.body().string();
        Log.e(TAG, responseString );
        return responseString;
    }

    private static NowWeather.HeWeather6Bean heWeather6Bean;

    //实况天气数据获取
    public static NowWeather.HeWeather6Bean getNow(String cityName) throws IOException{
        String response = run(Constant.NOWURL + "location=" + cityName + "&key=" + Constant.KEY);
        NowWeather nowWeather = gson.fromJson(response, NowWeather.class);
        heWeather6Bean = nowWeather.getHeWeather6().get(0);
        return heWeather6Bean;
    }


    //实况信息
    public static NowWeather.HeWeather6Bean.NowBean getNowBean(String cityName) throws IOException{
        return getNow(cityName).getNow();
    }

    //实况更新时间
    public static String getNowTime(){
        return heWeather6Bean.getUpdate().getLoc();
    }

    //城市列表数据获取
    public static List<Find.HeWeather6Bean.BasicBean> getCity(String cityName) throws IOException {
        String response = run(Constant.FINDURL + "location=" + cityName + "&key=" + Constant.KEY + "&group=cn");
        Find find = gson.fromJson(response, Find.class);
        return find.getHeWeather6().get(0).getBasic();
    }

    //生活指数数据获取
    public static List<LifeStyle.HeWeather6Bean.LifestyleBean> getLifeStyle(String cityName) throws IOException {
        String response = run(Constant.LIFESTYLEURL + "location=" + cityName + "&key=" + Constant.KEY);
        LifeStyle lifeStyle = gson.fromJson(response, LifeStyle.class);
        return lifeStyle.getHeWeather6().get(0).getLifestyle();
    }

    //天气预报数据获取
    public static List<WeatherForecast.HeWeather6Bean.DailyForecastBean> getForecast(String cityName) throws IOException {
        String response = run(Constant.FORECASTURL + "location=" + cityName + "&key=" + Constant.KEY);
        WeatherForecast weatherForecast = gson.fromJson(response, WeatherForecast.class);
        return weatherForecast.getHeWeather6().get(0).getDaily_forecast();
    }

    //空气质量实况获取
    public static AirNow.HeWeather6Bean.AirNowCityBean getAirNow(String cityName) throws IOException{
        String response = run(Constant.AIRURL + "location=" + cityName + "&key=" + Constant.KEY);
        AirNow airNow = gson.fromJson(response, AirNow.class);
        return airNow.getHeWeather6().get(0).getAir_now_city();
    }

    //根据cityName获取weatherMain
    public static WeatherMain getWM(String cityName) {

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




}
