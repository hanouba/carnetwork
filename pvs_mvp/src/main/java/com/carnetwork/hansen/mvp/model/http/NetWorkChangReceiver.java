package com.carnetwork.hansen.mvp.model.http;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;


/**
 * 创建者 by ${HanSir} on 2018/3/1.
 * 版权所有  WELLTRANS.
 * 说明
 */

public class NetWorkChangReceiver extends BroadcastReceiver {
    private static final String TAG = "NetWorkChangReceiver";
    private Activity activity;
    public NetWorkChangReceiver(Activity testCorActivity) {
        this.activity = testCorActivity;
    }

    /**
     * 获取连接类型
     *
     * @param type
     * @return
     */
    private String getConnectionType(int type) {
        String connType = "";
        if (type == ConnectivityManager.TYPE_MOBILE) {
            connType = "3G网络数据";
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            connType = "WIFI网络";
        }
        return connType;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {// 监听wifi的打开与关闭，与wifi的连接无关
            int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
            Log.e(TAG, "wifiState:" + wifiState);
            switch (wifiState) {
                case WifiManager.WIFI_STATE_DISABLED:
                    break;
                case WifiManager.WIFI_STATE_DISABLING:
                    break;
            }
        }
        // 监听网络连接，包括wifi和移动数据的打开和关闭,以及连接上可用的连接都会接到监听
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            //获取联网状态的NetworkInfo对象
            NetworkInfo info = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
            if (info != null) {
                //如果当前的网络连接成功并且网络连接可用
                if (NetworkInfo.State.CONNECTED == info.getState() && info.isAvailable()) {
                    if (info.getType() == ConnectivityManager.TYPE_WIFI || info.getType() == ConnectivityManager.TYPE_MOBILE) {
                        Log.i(TAG, getConnectionType(info.getType()) + "连上");
                    }
                } else {
                    Log.i(TAG, getConnectionType(info.getType()) + "断开");
                    intent = new Intent();
                    intent.setAction("hide_dialog");
                    activity.sendBroadcast(intent);
                    ToastUtils.showLong("网络连接失败,请检查你的网络后重试!");
                }
            }
        }
    }
}
