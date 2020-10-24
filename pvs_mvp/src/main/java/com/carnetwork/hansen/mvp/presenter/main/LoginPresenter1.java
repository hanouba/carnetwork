package com.carnetwork.hansen.mvp.presenter.main;


import android.text.TextUtils;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.google.gson.Gson;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.base.RxPresenter;
import com.carnetwork.hansen.mvp.contract.main.LoginContract1;
import com.carnetwork.hansen.mvp.model.DataManager;
import com.carnetwork.hansen.mvp.model.db.LoginInfo;
import com.carnetwork.hansen.mvp.model.http.manege.RetrofitUrlManager;
import com.carnetwork.hansen.util.RxUtil;
import com.carnetwork.hansen.widget.CommonSubscriber;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import okhttp3.HttpUrl;

public class LoginPresenter1 extends RxPresenter<LoginContract1.View> implements LoginContract1.Presenter {
    private String TAG = "LoginPresenter1";
    private DataManager mDataManager;

    @Inject
    public LoginPresenter1(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }



    @Override
    public LoginInfo getLoginInfo() {
        LoginInfo userInfo = mDataManager.getUserInfo();
        return userInfo;
    }

    @Override
    public boolean getRemberPsdState() {
        return false;
    }

    @Override
    public void login(String carno, String carLicence, String phone, String name) {
        Map<String, Object> map = new HashMap();
        map.put("carLicence", carLicence);
        map.put("carNo", carno);
        map.put("name", name);
        map.put("phone", phone);




    }


}
