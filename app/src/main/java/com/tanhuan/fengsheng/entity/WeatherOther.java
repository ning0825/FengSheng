package com.tanhuan.fengsheng.entity;

import com.tanhuan.fengsheng.bean.LifeStyle;

import java.io.Serializable;
import java.util.List;

public class WeatherOther implements Serializable {

    private String cityName;

    private String tmp;

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    //风况
    private String windDir;
    private String windSc;
    private String windSpd;

    //空气质量
    private String aqi;

    public boolean isHasDate() {
        return hasDate;
    }

    public void setHasDate(boolean hasDate) {
        this.hasDate = hasDate;
    }

    private String pm10;
    private String pm25;
    private boolean hasDate;

    //生活指数
    private List<LifeStyle.HeWeather6Bean.LifestyleBean> lifestyleBeans;

    public String getWindDir() {
        return windDir;
    }

    public void setWindDir(String windDir) {
        this.windDir = windDir;
    }

    public String getWindSc() {
        return windSc;
    }

    public void setWindSc(String windSc) {
        this.windSc = windSc;
    }

    public String getWindSpd() {
        return windSpd;
    }

    public void setWindSpd(String windSpd) {
        this.windSpd = windSpd;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public String getPm10() {
        return pm10;
    }

    public void setPm10(String pm10) {
        this.pm10 = pm10;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public List<LifeStyle.HeWeather6Bean.LifestyleBean> getLifestyleBeans() {
        return lifestyleBeans;
    }

    public void setLifestyleBeans(List<LifeStyle.HeWeather6Bean.LifestyleBean> lifestyleBeans) {
        this.lifestyleBeans = lifestyleBeans;
    }

}
