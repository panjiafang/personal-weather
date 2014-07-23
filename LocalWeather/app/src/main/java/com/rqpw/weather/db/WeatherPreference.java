package com.rqpw.weather.db;

import android.content.Context;

/**
 * Created by Pan Jiafang on 2014/7/20.
 */
public class WeatherPreference extends BasePreference {

    public WeatherPreference(Context context) {
        super(context);
        file = "weather";
    }

    public void saveWeather(String city, String weather){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(city, weather);
        editor.commit();
    }

    public String getWeather(String city){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getString(city, "");
    }

}
