package com.carnetwork.hansen.base;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatDelegate;

import com.carnetwork.hansen.app.MyApplication;
import com.carnetwork.hansen.di.component.ActivityComponent;
import com.carnetwork.hansen.di.component.DaggerActivityComponent;
import com.carnetwork.hansen.di.module.ActivityModule;


import java.util.List;

import javax.inject.Inject;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:MVP activity基类
 */
public abstract class BaseActivity<T extends BasePresenter> extends SimpleActivity implements com.carnetwork.hansen.base.BaseView {
    private FragmentManager supportFragmentManager = null;
    private BaseFragment mCurrentFragment = null;
    @Inject
    protected T mPresenter;


    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .appComponent(MyApplication.getAppComponent())
                .activityModule(getActivityModule())
                .build();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        initInject();
        if (mPresenter != null)
            mPresenter.attachView(this);
//        setTranslucentStatus();
    }


    @Override
    protected void onDestroy() {
        if (mPresenter != null)
            mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void useNightMode(boolean isNight) {
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(
                    AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
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

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    /**
     * 切换fragment
     *
     * @param fragment
     */
    public void switchFragment(BaseFragment fragment, int containerViewId) {
        if (fragment == null) return;
        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        if (!fragment.isAdded()) {
            if (mCurrentFragment == null)
                ft.add(containerViewId, fragment).show(fragment);
            else
                ft.add(containerViewId, fragment).hide(mCurrentFragment).show(fragment);
        } else {
            ft.hide(mCurrentFragment).show(fragment);
        }
        mCurrentFragment = fragment;
        ft.commit();
    }

    /**
     * 切换fragment 携带数据
     *
     * @param fragment
     * @param containerViewId
     * @param bundle
     */
    public void switchFragment(BaseFragment fragment, int containerViewId, Bundle bundle) {
        if (fragment == null) return;
        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        //ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        fragment.setArguments(bundle);
        if (!fragment.isAdded()) {
            if (mCurrentFragment == null)
                ft.add(containerViewId, fragment).show(fragment);
            else
                ft.add(containerViewId, fragment).hide(mCurrentFragment).show(fragment);
        } else {
            ft.hide(mCurrentFragment).show(fragment);
        }
        mCurrentFragment = fragment;
        ft.commit();
    }

    /**
     * 替换fragment
     *
     * @param fragment
     */
    protected void replaceFragment(BaseFragment fragment, int containerViewId) {
        if (fragment == null) return;
        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        if (mCurrentFragment == null) {
            ft.add(containerViewId, fragment);
        } else {
            ft.remove(mCurrentFragment);
            ft.replace(containerViewId, fragment);
        }
        mCurrentFragment = fragment;
        ft.commit();
    }
    /**
     * 替换fragment
     *
     * @param fragment
     */
    protected void replaceFragment(BaseFragment fragment, int containerViewId, Bundle bundle) {
        if (fragment == null) return;
        supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = supportFragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        if (mCurrentFragment == null) {
            ft.add(containerViewId, fragment);
        } else {
            ft.remove(mCurrentFragment);
            ft.add(containerViewId, fragment);
        }
        mCurrentFragment = fragment;
        ft.commit();
    }

    /**
     * 在异常情况下导致app重启或者内存不足回收时判断是否存在缓存的Fragment
     *
     * @return
     */
    protected boolean hasCacheFragment(@Nullable Bundle savedInstanceState) {
        return (savedInstanceState != null);
    }

    /**
     * 取出由于异常或者内存回收导致app错误后FragmentManager保存的Fragment
     *
     * @param fragment
     * @return
     */
    protected <T extends BaseFragment> T getCacheFragment(Class<? extends BaseFragment> fragment) {
        try {
            T ret = null;
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            for (Fragment fg : fragments) {
                if (fg.getClass().getName().equals(fragment.getName())) {
                    ret = (T) fg;
                }
                //如果已经存在的fragment里面有一个状态为show的则赋值给mCurrentFragment
                //表明app重启后将要显示的界面是哪个  便于点击其他界面的时候有个标记点
                if (!fg.isHidden())
                    mCurrentFragment = (T) fg;
            }
            if (ret == null)
                ret = (T) fragment.newInstance();
            return ret;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fg : fragments) {
            fg.onActivityResult(requestCode, resultCode, data);
        }
    }

    /**
     * 标记存在的fragment
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (supportFragmentManager != null) {
            List<Fragment> fragments = supportFragmentManager.getFragments();
            for (Fragment fg : fragments) {
                outState.putString(fg.getClass().getName(), fg.getClass().getName());
            }
        }
        super.onSaveInstanceState(outState);
    }



    @Override
    protected void onStop() {
        super.onStop();
    }

}