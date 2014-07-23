package com.rqpw.weather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rqpw.weather.R;
import com.rqpw.weather.db.SettingPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

/**
 * Created by Pan Jiafang on 2014/7/14.
 */
public class Adapter_WeatherListview extends BaseAdapter {

    private Context context;
    private JSONArray data;
    private SettingPreference settingPreference;

    public Adapter_WeatherListview(Context context, JSONArray data){
        this.context = context;
        this.data = data;
        settingPreference = new SettingPreference(context);
    }

    @Override
    public int getCount() {
        return data.length();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private static ViewHolder viewHolder;
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_weather_listview, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_date = (TextView) convertView.findViewById(R.id.item_weather_date);
            viewHolder.tv_desc = (TextView) convertView.findViewById(R.id.item_weather_desc);
            viewHolder.tv_temp_low = (TextView) convertView.findViewById(R.id.item_weather_temp_low);
            viewHolder.tv_temp_high = (TextView) convertView.findViewById(R.id.item_weather_temp_high);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        try {
            JSONObject obj = data.getJSONObject(position);
            String date = obj.getString("date");
            viewHolder.tv_date.setText(date);
            String temp;
            if(settingPreference.isShownAsC()) {//当前设置为摄氏度
                viewHolder.tv_temp_low.setText(obj.getString("tempMinC"));
                viewHolder.tv_temp_high.setText(obj.getString("tempMaxC"));
            }
            else {
                viewHolder.tv_temp_low.setText(obj.getString("tempMinF"));
                viewHolder.tv_temp_high.setText(obj.getString("tempMaxF"));
            }


            JSONArray array = obj.getJSONArray("weatherDesc");
            if(array.length() > 0){
                obj = array.getJSONObject(0);
                viewHolder.tv_desc.setText(obj.getString("value"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return convertView;
    }

    private class ViewHolder{
        TextView tv_date, tv_desc, tv_temp_low, tv_temp_high;
    }

}
