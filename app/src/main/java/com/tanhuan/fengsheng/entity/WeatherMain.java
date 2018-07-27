package com.tanhuan.fengsheng.entity;

import com.tanhuan.fengsheng.bean.LifeStyle;

import java.io.Serializable;
import java.util.List;

public class WeatherMain implements Serializable{

    private String cityName;
    private String tmp;
    private String cond;
    private String airQlty;
    private String udTime;

    private String date1;
    private String date2;
    private String date3;
    private String date4;

    public String getUdTime() {
        return udTime;
    }

    public void setUdTime(String udTime) {
        this.udTime = udTime;
    }

    private String date5;

    private String tmp1;
    private String tmp2;
    private String tmp3;
    private String tmp4;
    private String tmp5;

    private String cond1;
    private String cond2;
    private String cond3;
    private String cond4;
    private String cond5;

    //风况
    private String windDir;
    private String windSc;
    private String windSpd;

    //空气质量
    private String aqi;
    private String pm10;
    private String pm25;

    public boolean isHasDate() {
        return hasDate;
    }

    public void setHasDate(boolean hasDate) {
        this.hasDate = hasDate;
    }

    private boolean hasDate;

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


    //生活指数
    private List<LifeStyle.HeWeather6Bean.LifestyleBean> lifestyleBeans;

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getCond() {
        return cond;
    }

    public void setCond(String cond) {
        this.cond = cond;
    }

    public String getAirQlty() {
        return airQlty;
    }

    public void setAirQlty(String airQlty) {
        this.airQlty = airQlty;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getDate3() {
        return date3;
    }

    public void setDate3(String date3) {
        this.date3 = date3;
    }

    public String getDate4() {
        return date4;
    }

    public void setDate4(String date4) {
        this.date4 = date4;
    }

    public String getDate5() {
        return date5;
    }

    public void setDate5(String date5) {
        this.date5 = date5;
    }

    public String getTmp1() {
        return tmp1;
    }

    public void setTmp1(String tmp1) {
        this.tmp1 = tmp1;
    }

    public String getTmp2() {
        return tmp2;
    }

    public void setTmp2(String tmp2) {
        this.tmp2 = tmp2;
    }

    public String getTmp3() {
        return tmp3;
    }

    public void setTmp3(String tmp3) {
        this.tmp3 = tmp3;
    }

    public String getTmp4() {
        return tmp4;
    }

    public void setTmp4(String tmp4) {
        this.tmp4 = tmp4;
    }

    public String getTmp5() {
        return tmp5;
    }

    public void setTmp5(String tmp5) {
        this.tmp5 = tmp5;
    }

    public String getCond1() {
        return cond1;
    }

    public void setCond1(String cond1) {
        this.cond1 = cond1;
    }

    public String getCond2() {
        return cond2;
    }

    public void setCond2(String cond2) {
        this.cond2 = cond2;
    }

    public String getCond3() {
        return cond3;
    }

    public void setCond3(String cond3) {
        this.cond3 = cond3;
    }

    public String getCond4() {
        return cond4;
    }

    public void setCond4(String cond4) {
        this.cond4 = cond4;
    }

    public String getCond5() {
        return cond5;
    }

    public void setCond5(String cond5) {
        this.cond5 = cond5;
    }
}
