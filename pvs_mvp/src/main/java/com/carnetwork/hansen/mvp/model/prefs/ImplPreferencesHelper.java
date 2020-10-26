package com.carnetwork.hansen.mvp.model.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.carnetwork.hansen.app.MyApplication;
import com.carnetwork.hansen.mvp.model.db.LoginInfo;


import javax.inject.Inject;

/**
 * @author: Est <codeest.dev@gmail.com>
 * @date: 2017/4/21
 * @description:
 */

public class ImplPreferencesHelper implements PreferencesHelper {

    private static final String DEFAULT_TOKEN = "token";


    private static final String SHAREDPREFERENCES_NAME = "my_sp";

    private final SharedPreferences mSPrefs;

    @Inject
    public ImplPreferencesHelper() {
        mSPrefs = MyApplication.getInstance().getSharedPreferences(SHAREDPREFERENCES_NAME, Context.MODE_PRIVATE);
    }


    @Override
    public String getToken() {
        return mSPrefs.getString(DEFAULT_TOKEN,"");
    }

    @Override
    public void setToken(String token) {
        mSPrefs.edit().putString(DEFAULT_TOKEN, token).apply();
    }
}


