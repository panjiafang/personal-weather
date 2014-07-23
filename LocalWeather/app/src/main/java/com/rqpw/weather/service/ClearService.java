package com.rqpw.weather.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.IBinder;
import android.util.Log;

import com.rqpw.weather.db.SettingPreference;

/**
 * Created by Pan Jiafang on 2014/7/23.
 */
public class ClearService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.e("weather", "service run");

        SettingPreference settingPreference = new SettingPreference(this);
        settingPreference.saveCurrentBG(settingPreference.getDefaultColor());
        settingPreference.saveDayListBG(settingPreference.getDefaultColor());
        settingPreference.saveAppBG(Color.argb(255, 255, 255, 255));
        return super.onStartCommand(intent, flags, startId);
    }
}
