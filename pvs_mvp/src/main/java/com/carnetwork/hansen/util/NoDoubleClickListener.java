package com.carnetwork.hansen.util;

import android.view.View;

import java.util.Calendar;

/**
 * Create by HanN on 2019/8/23
 * 注释:
 */
public abstract class NoDoubleClickListener implements View.OnClickListener {

    public static final int MIN_CLICK_DELAY_TIME = 1000;
    private long lastClickTime = 0;

    protected abstract void onNoDoubleClick(View v);
    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }

}
