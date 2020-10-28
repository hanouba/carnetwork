package com.carnetwork.hansen.di.module;

import android.app.Activity;
import androidx.fragment.app.Fragment;

import com.carnetwork.hansen.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:
 */

@Module
public class FragmentModule {

    private Fragment fragment;

    public FragmentModule(Fragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public Activity provideActivity() {
        return fragment.getActivity();
    }
}
