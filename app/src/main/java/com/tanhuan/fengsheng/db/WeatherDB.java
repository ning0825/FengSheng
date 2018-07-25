package com.tanhuan.fengsheng.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class WeatherDB {
    //数据库名
    public static final String DB_NAME = "city";
    //数据库版本
    public static final int VERSION = 1;

    private static WeatherDB weatherDB;

    private SQLiteDatabase db;

    private WeatherDB(Context context) {
        WeatherOpenHelper weatherOpenHelper = new WeatherOpenHelper(context, DB_NAME, null, VERSION);
        db = weatherOpenHelper.getWritableDatabase();
    }

    //获取WeatherDB实例
    public synchronized static WeatherDB getInstance(Context context) {
        if (weatherDB == null) {
            weatherDB = new WeatherDB(context);
        }
        return weatherDB;
    }

    //存储城市
    public void saveCity(String cityName) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("city_name", cityName);
        db.insert("city", null, contentValues);
    }

    //读取城市
    public List<String> getCity() {
        List<String> list = new ArrayList<>();
        Cursor cursor = db.query("city", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String cityName = cursor.getString(cursor.getColumnIndex("city_name"));
                list.add(cityName);
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }
        return list;
    }

    //删除城市
    public void deleteCity(String cityName) {
        db.delete("city", "city_name=?", new String[]{cityName});
    }

}
