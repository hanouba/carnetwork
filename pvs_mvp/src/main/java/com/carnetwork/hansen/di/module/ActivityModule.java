package com.carnetwork.hansen.di.module;

import android.app.Activity;
import com.carnetwork.hansen.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:
 */

@Module
public class ActivityModule {
    private Activity mActivity;

    public ActivityModule(Activity activity) {
        this.mActivity = activity;
    }

    @Provides
    @ActivityScope
    public Activity provideActivity() {
        return mActivity;
    }
}
