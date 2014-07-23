package com.rqpw.weather.db;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Pan Jiafang on 2014/7/20.
 */
public class CityPreference extends BasePreference {

    public CityPreference(Context context) {
        super(context);
        file = "city";
    }

    /**
     * 保存所在地区
     * @param value
     */
    public void saveLocalArea(String value){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("local", value);
        editor.commit();
    }

    /**
     * 获取当前地区
     * @return
     */
    public String getLocalArea(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getString("local", "");
    }

    public boolean addCity(String value){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        try {
            String citys = getCity();
            JSONArray array;
            if(!citys.equals(""))
                array = new JSONArray(citys);
            else
                array = new JSONArray();
            JSONObject city;
            for(int i = 0; i < array.length(); i++){
                city = array.getJSONObject(i);
                if(city.getString("city").equals(value))
                    return false;
            }
            city = new JSONObject();
            city.put("city", value);
            array.put(city);
            editor.putString("citys", array.toString());
            editor.commit();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void removeCity(String area){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        try {
            String citys = getCity();
            JSONArray array = null;
            JSONArray temp = null;
            if(!citys.equals(""))
                array = new JSONArray(citys);
            if(array != null && array.length() > 0){
                temp = new JSONArray();
                JSONObject object = null;
                for(int i = 0; i < array.length(); i++){
                    object = array.getJSONObject(i);
                    if(!object.getString("city").equals(area))
                        temp.put(object);
                }

                if(array.length() != 0)
                    editor.putString("citys", temp.toString());
                else
                    editor.putString("citys", "");
                editor.commit();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getCity(){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getString("citys", "");
    }

    public void addCityName(String key, String name){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(key, name);
        editor.commit();
    }

    public void removeCityName(String key){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public String getCityName(String key){
        sharedPreferences = context.getSharedPreferences(file, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, null);
    }
}
