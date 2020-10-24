package com.carnetwork.hansen.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.carnetwork.hansen.app.MyApplication;
import com.carnetwork.hansen.di.component.DaggerFragmentComponent;
import com.carnetwork.hansen.di.component.FragmentComponent;
import com.carnetwork.hansen.di.module.FragmentModule;

import javax.inject.Inject;
/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:MVP Fragment基类
 */
public abstract class BaseFragment<T extends BasePresenter> extends SimpleFragment implements com.carnetwork.hansen.base.BaseView {

    @Inject
    protected T mPresenter;

    protected FragmentComponent getFragmentComponent(){
        return DaggerFragmentComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .fragmentModule(getFragmentModule())
                .build();
    }

    protected FragmentModule getFragmentModule(){
        return new FragmentModule(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initInject();
        mPresenter.attachView(this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        if (mPresenter != null) mPresenter.detachView();
        super.onDestroyView();
    }

    @Override
    public void useNightMode(boolean isNight) {

    }

    @Override
    public void stateError() {

    }

    @Override
    public void stateEmpty() {

    }

    @Override
    public void stateLoading() {

    }

    @Override
    public void stateMain() {

    }

    protected abstract void initInject();
}