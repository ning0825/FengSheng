package com.tanhuan.fengsheng.util;

import android.util.Log;

import com.google.gson.Gson;
import com.tanhuan.fengsheng.bean.AirNow;
import com.tanhuan.fengsheng.bean.Find;
import com.tanhuan.fengsheng.bean.LifeStyle;
import com.tanhuan.fengsheng.bean.NowWeather;
import com.tanhuan.fengsheng.bean.WeatherForecast;

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


}
