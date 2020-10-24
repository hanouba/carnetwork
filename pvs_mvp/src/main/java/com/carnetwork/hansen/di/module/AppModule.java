package com.carnetwork.hansen.di.module;


import com.carnetwork.hansen.app.MyApplication;
import com.carnetwork.hansen.mvp.model.DataManager;
import com.carnetwork.hansen.mvp.model.db.DBHelper;

import com.carnetwork.hansen.mvp.model.db.GreenHelper;
import com.carnetwork.hansen.mvp.model.http.HttpHelper;
import com.carnetwork.hansen.mvp.model.http.RetrofitHelper;
import com.carnetwork.hansen.mvp.model.prefs.ImplPreferencesHelper;
import com.carnetwork.hansen.mvp.model.prefs.PreferencesHelper;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:
 */
@Module
public class AppModule {
    private final MyApplication application;

    public AppModule(MyApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    MyApplication provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    HttpHelper provideHttpHelper(RetrofitHelper retrofitHelper) {
        return retrofitHelper;
    }



    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(ImplPreferencesHelper implPreferencesHelper) {
        return implPreferencesHelper;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(HttpHelper httpHelper, PreferencesHelper preferencesHelper,DBHelper dbHelper) {
        return new DataManager(httpHelper,  preferencesHelper,dbHelper);
    }

    @Provides
    @Singleton
    DBHelper provideDBHelper(GreenHelper greenHelper) {
        return greenHelper;
    }
}
