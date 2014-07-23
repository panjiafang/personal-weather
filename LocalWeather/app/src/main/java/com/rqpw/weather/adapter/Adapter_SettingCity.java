package com.rqpw.weather.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rqpw.weather.MainActivity;
import com.rqpw.weather.R;
import com.rqpw.weather.db.CityPreference;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Pan Jiafang on 2014/7/21.
 */
public class Adapter_SettingCity extends BaseAdapter {

    private MainActivity context;
    private JSONArray citys;
    private CityPreference cityPreference;

    public Adapter_SettingCity(MainActivity context, JSONArray citys){
        this.context = context;
        this.citys = citys;
        cityPreference = new CityPreference(context);
    }

    @Override
    public int getCount() {
        return citys == null ? 0 : citys.length();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_setting_citys, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_area = (TextView) convertView.findViewById(R.id.item_setting_city_tv_area);
            viewHolder.tv_name = (TextView) convertView.findViewById(R.id.item_setting_city_tv_name);
            viewHolder.iv = (ImageView) convertView.findViewById(R.id.item_setting_city_iv);
            convertView.setTag(viewHolder);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();

        try {
            final String area = citys.getJSONObject(position).getString("city");
            final String name = cityPreference.getCityName(area);
            viewHolder.tv_area.setText(area);
            viewHolder.tv_name.setText(name);
            viewHolder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("weather", area);
                    cityPreference.removeCityName(area);
                    cityPreference.removeCity(area);
                    context.updateCitys(area);
                    update();
                    notifyDataSetChanged();
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return convertView;
    }

    private void update(){
        String cityStr = cityPreference.getCity();
        if(!cityStr.equals("")){
            try {
                citys = new JSONArray(cityStr);
                notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class ViewHolder{
        TextView tv_area, tv_name;
        ImageView iv;
    }
}
