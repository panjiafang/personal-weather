package com.rqpw.weather.db;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Pan Jiafang on 2014/7/15.
 */
public class BasePreference {
    protected Context context;
    protected SharedPreferences sharedPreferences;
    protected SharedPreferences.Editor editor;

    protected String file = "weather";

    public BasePreference(Context context){
        this.context = context;
    }

}
