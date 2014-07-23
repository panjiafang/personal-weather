package com.rqpw.weather.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rqpw.weather.R;
import com.rqpw.weather.adapter.Adapter_WeatherListview;
import com.rqpw.weather.db.CityPreference;
import com.rqpw.weather.db.SettingPreference;
import com.rqpw.weather.db.WeatherPreference;
import com.rqpw.weather.network.NetWorkUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Pan Jiafang on 2014/7/14.
 */
public class Weather extends Fragment implements View.OnClickListener {

    private TextView tv_temp, tv_desc, tv_wind, tv_time, tv_area;
    private ImageView iv_sync;
    private ListView listView;
    private ProgressBar progressBar;

    private RelativeLayout layout_app, layout_current;

    private ProgressBar progressBar_load;

    private GetInfoTask task;

    public String area;
    private SettingPreference settingPreference;
    private WeatherPreference weatherPreference;

    private Adapter_WeatherListview adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_weather, null);

        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        init(view);

        return view;
    }

    private void init(View view){
        tv_temp = (TextView) view.findViewById(R.id.weather_tv_temp);
        tv_desc = (TextView) view.findViewById(R.id.weather_tv_desc);
        tv_wind = (TextView) view.findViewById(R.id.weather_tv_wind);
        tv_time = (TextView) view.findViewById(R.id.weather_tv_time);
        tv_area = (TextView) view.findViewById(R.id.weather_tv_area);
        iv_sync = (ImageView) view.findViewById(R.id.weather_iv_sync);
        iv_sync.setOnClickListener(this);
        listView = (ListView) view.findViewById(R.id.weather_listview);
        layout_app = (RelativeLayout) view.findViewById(R.id.weather_layout_app);
        layout_current = (RelativeLayout) view.findViewById(R.id.weather_layout_current);
        progressBar = (ProgressBar) view.findViewById(R.id.weather_area_progressbar);
        progressBar_load = (ProgressBar) view.findViewById(R.id.weather_progressbar_load);

        settingPreference = new SettingPreference(getActivity());
        weatherPreference = new WeatherPreference(getActivity());

        int curbg = settingPreference.getCurrentBG();
        int daylistbg = settingPreference.getDayListBG();
        layout_current.setBackgroundColor(curbg);
        listView.setBackgroundColor(daylistbg);

        String app_bg_path = settingPreference.getAppBGPicPath();
        if(app_bg_path.equals("")){
            int appbg = settingPreference.getAppBG();
            layout_app.setBackgroundColor(appbg);
        }
        else{
            Uri picUri = Uri.parse(app_bg_path);
            try {
                InputStream picIS = getActivity().getContentResolver().openInputStream(picUri);
                Drawable bitmapDrawable = BitmapDrawable.createFromStream(picIS, null);
                layout_app.setBackgroundDrawable(bitmapDrawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        task = new GetInfoTask();
        task.execute(area);
        new GetLocationTask().execute(area);
    }

    public void setWeatherInfo(String area){
        this.area = area;
    }

    public void updateUI(){
        settingPreference = new SettingPreference(getActivity());
        int curbg = settingPreference.getCurrentBG();
        int daylistbg = settingPreference.getDayListBG();
        layout_current.setBackgroundColor(curbg);
        listView.setBackgroundColor(daylistbg);

        String app_bg_path = settingPreference.getAppBGPicPath();
        if(app_bg_path.equals("")){
            int appbg = settingPreference.getAppBG();
            layout_app.setBackgroundColor(appbg);
        }
        else{
            Uri picUri = Uri.parse(app_bg_path);
            try {
                InputStream picIS = getActivity().getContentResolver().openInputStream(picUri);
                Drawable bitmapDrawable = BitmapDrawable.createFromStream(picIS, null);
                layout_app.setBackgroundDrawable(bitmapDrawable);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        try {
            String weather = weatherPreference.getWeather(area);
            JSONArray array;
            JSONObject temp;

            if(!weather.equals("")) {
                JSONObject object = new JSONObject(weather);
                if(object.has("current_condition")){
                    array = object.getJSONArray("current_condition");
                    if(array.length() > 0){
                        temp = array.getJSONObject(0);
                        if(settingPreference.isShownAsC())//当前设置为摄氏度
                            tv_temp.setText(temp.getString("temp_C")+"°");
                        else
                            tv_temp.setText(temp.getString("temp_F")+"°");
                        String direct = temp.getString("winddir16Point");
                        String speed = temp.getString("windspeedKmph");
                        tv_wind.setText("Wind: " + direct + " " + speed + "Km/h");
                        String time = temp.getString("localObsDateTime");
                        tv_time.setText("Last Updated "+ time.substring(time.indexOf(" ")));
                        array = temp.getJSONArray("weatherDesc");
                        if(array.length() > 0)
                            temp = array.getJSONObject(0);
                        tv_desc.setText(temp.getString("value"));
                    }
                }
                if(object.has("weather")){
                    adapter = new Adapter_WeatherListview(getActivity(), object.getJSONArray("weather"));
                    listView.setAdapter(adapter);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if(v == iv_sync){
            progressBar_load.setVisibility(View.VISIBLE);
            task = new GetInfoTask();
            task.execute(area);
        }
    }

    private class GetInfoTask extends AsyncTask<String, String, String>{
        private String area;
        @Override
        protected String doInBackground(String... params) {
            area = params[0];
            return NetWorkUtil.getWeatherInfo(area);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressBar_load.setVisibility(View.GONE);

            if(!isCancelled() && result != null){
                try {
                    JSONObject obj = new JSONObject(result);
                    obj = obj.getJSONObject("data");
                    JSONArray array;
                    JSONObject temp;
                    if(obj.has("error")){

                    }
                    else{

                        weatherPreference.saveWeather(area, obj.toString());

                        if(obj.has("current_condition")){
                            array = obj.getJSONArray("current_condition");
                            if(array.length() > 0){
                                temp = array.getJSONObject(0);
                                if(settingPreference.isShownAsC())//当前设置为摄氏度
                                    tv_temp.setText(temp.getString("temp_C")+"°");
                                else
                                    tv_temp.setText(temp.getString("temp_F")+"°");
                                String direct = temp.getString("winddir16Point");
                                String speed = temp.getString("windspeedKmph");
                                tv_wind.setText("Wind: " + direct + " " + speed + "Km/h");
                                String time = temp.getString("localObsDateTime");
                                tv_time.setText("Last Updated "+ time.substring(time.indexOf(" ")));
                                array = temp.getJSONArray("weatherDesc");
                                if(array.length() > 0)
                                    temp = array.getJSONObject(0);
                                tv_desc.setText(temp.getString("value"));
                            }
                        }
                        if(obj.has("weather")){
                            adapter = new Adapter_WeatherListview(getActivity(), obj.getJSONArray("weather"));
                            listView.setAdapter(adapter);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class GetLocationTask extends AsyncTask<String, String, String>{
        private String area;
        private String name;
        @Override
        protected String doInBackground(String... params) {
            area = params[0];
            return NetWorkUtil.getLocation(area);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressBar.setVisibility(View.GONE);

            if(result != null){
                try {
                    JSONObject object = new JSONObject(result);
                    if(object.has("search_api")){
                        object = object.getJSONObject("search_api");
                        if(object.has("result")){
                            JSONArray array = object.getJSONArray("result");
                            if(array.length() > 0){
                                object = array.getJSONObject(0);
                                array = object.getJSONArray("areaName");
                                JSONObject temp = null;
                                if(array.length() > 0)
                                    temp = array.getJSONObject(0);
                                if(temp != null)
                                    name = temp.getString("value");
                                array = object.getJSONArray("country");
                                if(array.length() > 0)
                                    temp = array.getJSONObject(0);
                                if(temp != null)
                                    name = name +","+ temp.getString("value");
                                tv_area.setText(name);
                                CityPreference cityPreference = new CityPreference(getActivity());
                                cityPreference.addCityName(area, name);
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
