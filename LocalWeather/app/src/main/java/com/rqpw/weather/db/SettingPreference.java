package com.rqpw.weather.db;

import android.content.Context;

/**
 * Created by Pan Jiafang on 2014/7/15.
 */
public class SettingPreference extends BasePreference {

    public SettingPreference(Context context) {
        super(context);
        file = "setting";
    }

    public boolean isShownAsC(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        boolean degree_c = sharedPreferences.getBoolean("degree_c", false);
        return degree_c;
    }

    public void saveDegreeShow(boolean isC){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putBoolean("degree_c", isC);
        editor.commit();
    }

    public int getCurrentBG(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("current_bg", 0x11111111);
    }

    public void saveCurrentBG(int color){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("current_bg", color);
        editor.commit();
    }

    public int getDefaultColor(){
        return 0x11111111;
    }

    public int getDayListBG(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("daylist_bg", 0x11111111);
    }

    public void saveDayListBG(int color){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("daylist_bg", color);
        editor.commit();
    }

    public int getAppBG(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getInt("app_bg", 0xFFFFFFFF);
    }

    public void saveAppBG(int color){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putInt("app_bg", color);
        editor.commit();
    }

    public String getAppBGPicPath(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getString("app_bg_pic", "");
    }

    public void saveAppBGPicPath(String path){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("app_bg_pic", path);
        editor.commit();
    }
}
