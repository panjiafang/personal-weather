package com.rqpw.weather.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Pan Jiafang on 2014/7/20.
 */
public class MarQueeTextView extends TextView {
    public MarQueeTextView(Context context) {
        super(context);
    }

    public MarQueeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarQueeTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean isFocused() {
        return true;
    }
}
