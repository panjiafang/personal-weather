package com.rqpw.weather.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.rqpw.weather.MainActivity;
import com.rqpw.weather.R;
import com.rqpw.weather.adapter.Adapter_SettingCity;
import com.rqpw.weather.db.CityPreference;
import com.rqpw.weather.db.SettingPreference;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by Pan Jiafang on 2014/7/15.
 */
public class Setting extends Fragment implements SeekBar.OnSeekBarChangeListener{

    private SeekBar seekBar_cred, seekBar_cgreen, seekBar_cblue, seekBar_calpha, seekBar_dred, seekBar_dgreen, seekBar_dblue, seekBar_dalpha, seekBar_ared, seekBar_agreen, seekBar_ablue;
    private TextView tv_cc, tv_cd, tv_dc, tv_dd, tv_ac, tv_ad;
    private RelativeLayout bg_ca, bg_da, bg_aa;

    private ListView listview;

    private RadioGroup rg_degree, rg_bg;
    private RadioButton rb_c, rb_f, rb_color, rb_pic;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        init(view);
        return view;
    }

    private void init(View view) {
        seekBar_cred = (SeekBar) view.findViewById(R.id.setting_seekbar_red);
        seekBar_cgreen = (SeekBar) view.findViewById(R.id.setting_seekbar_green);
        seekBar_cblue = (SeekBar) view.findViewById(R.id.setting_seekbar_blue);
        seekBar_calpha = (SeekBar) view.findViewById(R.id.setting_seekbar_alpha);

        seekBar_dred = (SeekBar) view.findViewById(R.id.setting_seekbar_dred);
        seekBar_dgreen = (SeekBar) view.findViewById(R.id.setting_seekbar_dgreen);
        seekBar_dblue = (SeekBar) view.findViewById(R.id.setting_seekbar_dblue);
        seekBar_dalpha = (SeekBar) view.findViewById(R.id.setting_seekbar_dalpha);

        seekBar_ared = (SeekBar) view.findViewById(R.id.setting_seekbar_ared);
        seekBar_agreen = (SeekBar) view.findViewById(R.id.setting_seekbar_agreen);
        seekBar_ablue = (SeekBar) view.findViewById(R.id.setting_seekbar_ablue);

        tv_cc = (TextView) view.findViewById(R.id.setting_tv_ccwbg_effect);
        tv_cd = (TextView) view.findViewById(R.id.setting_tv_cdwbg_effect);
        tv_dc = (TextView) view.findViewById(R.id.setting_tv_dcwbg_effect);
        tv_dd = (TextView) view.findViewById(R.id.setting_tv_ddwbg_effect);
        tv_ac = (TextView) view.findViewById(R.id.setting_tv_acwbg_effect);
        tv_ad = (TextView) view.findViewById(R.id.setting_tv_adwbg_effect);

        bg_ca = (RelativeLayout) view.findViewById(R.id.setting_layout_cwbg);
        bg_da = (RelativeLayout) view.findViewById(R.id.setting_layout_dwbg);
        bg_aa = (RelativeLayout) view.findViewById(R.id.setting_layout_awbg);

        rg_degree = (RadioGroup) view.findViewById(R.id.setting_rg_degree);
        rg_bg = (RadioGroup) view.findViewById(R.id.setting_rg_appbg);
        rb_c = (RadioButton) view.findViewById(R.id.setting_rb_c);
        rb_f = (RadioButton) view.findViewById(R.id.setting_rb_f);
        rb_color = (RadioButton) view.findViewById(R.id.setting_rb_bg_color);
        rb_pic = (RadioButton) view.findViewById(R.id.setting_rb_bg_photo);

        listview = (ListView) view.findViewById(R.id.setting_listview);
        CityPreference cityPreference = new CityPreference(getActivity());
        String cityStr = cityPreference.getCity();
        if(!cityStr.equals("")){
            try {
                JSONArray array = new JSONArray(cityStr);
                Adapter_SettingCity adapter_settingCity = new Adapter_SettingCity((MainActivity) getActivity(), array);

                DisplayMetrics displayMetrics = new DisplayMetrics();
                getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                int height = (int)displayMetrics.density * 48 * array.length() + array.length() - 1;
                listview.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
                listview.setAdapter(adapter_settingCity);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        seekBar_cred.setOnSeekBarChangeListener(this);
        seekBar_cgreen.setOnSeekBarChangeListener(this);
        seekBar_cblue.setOnSeekBarChangeListener(this);
        seekBar_calpha.setOnSeekBarChangeListener(this);
        seekBar_dred.setOnSeekBarChangeListener(this);
        seekBar_dgreen.setOnSeekBarChangeListener(this);
        seekBar_dblue.setOnSeekBarChangeListener(this);
        seekBar_dalpha.setOnSeekBarChangeListener(this);
        seekBar_ared.setOnSeekBarChangeListener(this);
        seekBar_agreen.setOnSeekBarChangeListener(this);
        seekBar_ablue.setOnSeekBarChangeListener(this);

        SettingPreference settingPreference = new SettingPreference(getActivity());
        boolean shownAsC = settingPreference.isShownAsC();
        int current_bg = settingPreference.getCurrentBG();
        int daylist_bg = settingPreference.getDayListBG();
        String app_pic_path = settingPreference.getAppBGPicPath();
        int app_bg =settingPreference.getAppBG();

        if(shownAsC)
            rb_c.setChecked(true);
        else
            rb_f.setChecked(true);
        if(app_pic_path.equals("")) {
            rb_color.setChecked(true);
            bg_ca.setBackgroundColor(app_bg);
            bg_da.setBackgroundColor(app_bg);
            bg_aa.setBackgroundColor(app_bg);
        }
        else {
            rb_pic.setChecked(true);
            Uri picUri = Uri.parse(app_pic_path);
            Bitmap bitmap = lessenUriImage(getPath(picUri));
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
            bg_ca.setBackgroundDrawable(bitmapDrawable);
            bg_da.setBackgroundDrawable(bitmapDrawable);
            bg_aa.setBackgroundDrawable(bitmapDrawable);
        }

        tv_ac.setBackgroundColor(current_bg);
        tv_dc.setBackgroundColor(current_bg);
        tv_ac.setBackgroundColor(current_bg);

        tv_ad.setBackgroundColor(daylist_bg);
        tv_cd.setBackgroundColor(daylist_bg);
        tv_dd.setBackgroundColor(daylist_bg);

        seekBar_cred.setProgress(Color.red(current_bg));
        seekBar_cgreen.setProgress(Color.green(current_bg));
        seekBar_cblue.setProgress(Color.blue(current_bg));
        seekBar_calpha.setProgress(Color.alpha(current_bg));

        seekBar_dred.setProgress(Color.red(daylist_bg));
        seekBar_dgreen.setProgress(Color.green(daylist_bg));
        seekBar_dblue.setProgress(Color.blue(daylist_bg));
        seekBar_dalpha.setProgress(Color.alpha(daylist_bg));

        seekBar_ared.setProgress(Color.red(app_bg));
        seekBar_agreen.setProgress(Color.green(app_bg));
        seekBar_ablue.setProgress(Color.blue(app_bg));

        rb_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_pic.setChecked(true);

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setDataAndType(MediaStore.Images.Media.INTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent, 0);
            }
        });
        rb_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rb_color.setChecked(true);
                SettingPreference settingPreference = new SettingPreference(getActivity());
                settingPreference.saveAppBGPicPath("");

                int color = Color.argb(255, seekBar_ared.getProgress(), seekBar_agreen.getProgress(), seekBar_ablue.getProgress());
                bg_ca.setBackgroundColor(color);
                bg_da.setBackgroundColor(color);
                bg_aa.setBackgroundColor(color);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri picUri = data.getData();
        Bitmap bitmap = lessenUriImage(getPath(picUri));
        BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
        bg_ca.setBackgroundDrawable(bitmapDrawable);
        bg_da.setBackgroundDrawable(bitmapDrawable);
        bg_aa.setBackgroundDrawable(bitmapDrawable);

        SettingPreference settingPreference = new SettingPreference(getActivity());
        settingPreference.saveAppBGPicPath(picUri.toString());
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int color = Color.argb(seekBar_calpha.getProgress(), seekBar_cred.getProgress(), seekBar_cgreen.getProgress(), seekBar_cblue.getProgress());
        tv_cc.setBackgroundColor(color);
        tv_dc.setBackgroundColor(color);
        tv_ac.setBackgroundColor(color);

        color = Color.argb(seekBar_dalpha.getProgress(), seekBar_dred.getProgress(), seekBar_dgreen.getProgress(), seekBar_dblue.getProgress());
        tv_cd.setBackgroundColor(color);
        tv_dd.setBackgroundColor(color);
        tv_ad.setBackgroundColor(color);

        if(rb_color.isChecked()){
            color = Color.argb(255, seekBar_ared.getProgress(), seekBar_agreen.getProgress(), seekBar_ablue.getProgress());
            bg_ca.setBackgroundColor(color);
            bg_da.setBackgroundColor(color);
            bg_aa.setBackgroundColor(color);
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onDetach() {
        SettingPreference settingPreference = new SettingPreference(getActivity());
        settingPreference.saveAppBG(Color.argb(255, seekBar_ared.getProgress(), seekBar_agreen.getProgress(), seekBar_ablue.getProgress()));
        settingPreference.saveCurrentBG(Color.argb(seekBar_calpha.getProgress(), seekBar_cred.getProgress(), seekBar_cgreen.getProgress(), seekBar_cblue.getProgress()));
        settingPreference.saveDayListBG(Color.argb(seekBar_dalpha.getProgress(), seekBar_dred.getProgress(), seekBar_dgreen.getProgress(), seekBar_dblue.getProgress()));

        settingPreference.saveDegreeShow(rb_c.isChecked());

        ((MainActivity)getActivity()).updateUI();

        super.onDetach();
    }

    public String getPath(Uri uri)
    {
        String[] projection = {MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    /**
     * use to lessen pic 50%
     * @param path sd card path
     * @return bitmap
     */
    public final static Bitmap lessenUriImage(String path)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options); //此时返回 bm 为空
        options.inJustDecodeBounds = false; //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        int be = (int)(options.outHeight / (float)320);
        if (be <= 0)
            be = 1;
        options.inSampleSize = be; //重新读入图片，注意此时已经把 options.inJustDecodeBounds 设回 false 了
        bitmap=BitmapFactory.decodeFile(path,options);
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        System.out.println(w+" "+h); //after zoom
        return bitmap;
    }
}
