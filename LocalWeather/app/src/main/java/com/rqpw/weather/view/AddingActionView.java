package com.rqpw.weather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rqpw.weather.R;

/**
 * Created by Pan Jiafang on 2014/7/21.
 */
public class AddingActionView extends RelativeLayout {

    public EditText et;
    public TextView tv;

    private Context context;

    public AddingActionView(Context context) {
        super(context);
        this.context = context;
        init();
    }

    public AddingActionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public AddingActionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    private void init(){
        View view = LayoutInflater.from(context).inflate(R.layout.view_adding, null);
        et = (EditText) view.findViewById(R.id.view_adding_et);
        tv = (TextView) view.findViewById(R.id.view_adding_tv);

        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.addRule(CENTER_VERTICAL);
        addView(view, params);
    }
}
