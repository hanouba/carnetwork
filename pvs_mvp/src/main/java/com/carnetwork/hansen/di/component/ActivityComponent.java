package com.carnetwork.hansen.di.component;

import android.app.Activity;


import com.carnetwork.hansen.di.module.ActivityModule;
import com.carnetwork.hansen.di.scope.ActivityScope;
import com.carnetwork.hansen.ui.main.activity.CarInfosActivity;
import com.carnetwork.hansen.ui.main.activity.LoginActivity;
import com.carnetwork.hansen.ui.main.activity.MainActivity;
import com.carnetwork.hansen.ui.main.activity.SplashActivity;

import dagger.Component;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:
 */
@ActivityScope
@Component(dependencies = com.carnetwork.hansen.di.component.AppComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    Activity getActivity();
    void inject(SplashActivity splashActivity);
    void inject(MainActivity mainActivity);
    void inject(LoginActivity loginActivity1);

    void inject(CarInfosActivity carInfosActivity);
}
