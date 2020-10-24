package com.carnetwork.hansen.di.component;

import com.carnetwork.hansen.app.MyApplication;
import com.carnetwork.hansen.di.module.AppModule;
import com.carnetwork.hansen.di.module.HttpModule;

import com.carnetwork.hansen.mvp.model.DataManager;
import com.carnetwork.hansen.mvp.model.db.GreenHelper;
import com.carnetwork.hansen.mvp.model.http.RetrofitHelper;
import com.carnetwork.hansen.mvp.model.prefs.ImplPreferencesHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:
 */
@Singleton
@Component(modules = {AppModule.class, HttpModule.class})
public interface AppComponent {

    MyApplication getContext();  // 提供App的Context

    DataManager getDataManager(); //数据中心

    RetrofitHelper retrofitHelper();  //提供http的帮助类
    GreenHelper greenHelper();

    ImplPreferencesHelper preferencesHelper(); //提供sp帮助类
}
