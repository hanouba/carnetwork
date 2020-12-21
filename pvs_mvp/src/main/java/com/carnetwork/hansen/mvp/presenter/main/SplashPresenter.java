package com.carnetwork.hansen.mvp.presenter.main;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.base.RxPresenter;
import com.carnetwork.hansen.mvp.contract.main.SplashContract;
import com.carnetwork.hansen.mvp.model.DataManager;
import com.carnetwork.hansen.mvp.model.db.LoginInfo;
import com.carnetwork.hansen.mvp.model.http.manege.RetrofitUrlManager;
import com.carnetwork.hansen.ui.main.activity.LoginActivity;
import com.carnetwork.hansen.ui.main.activity.MainActivity;
import com.carnetwork.hansen.util.RxUtil;
import com.carnetwork.hansen.widget.CommonSubscriber;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.functions.Consumer;
import okhttp3.HttpUrl;

/**
 * Created by codeest on 16/8/15.
 */

public class SplashPresenter extends RxPresenter<SplashContract.View> implements SplashContract.Presenter {
    private static final String[] PERMISSIONS = new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CALL_PHONE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_PHONE_STATE,
    };

    private DataManager mDataManager;


    @Inject
    public SplashPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }


    /**
     * 权限检查
     *
     * @param rxPermissions 申请的权限集合
     * @param activity      上上下文
     */
    @Override
    public void checkPermissions(final RxPermissions rxPermissions, final Activity activity) {
        addSubscribe(rxPermissions
                .request(PERMISSIONS)
                .subscribe(granted -> {
                    LogUtils.d("获取权限");
                    jumpToLogin(activity);
                })
        );
    }

    /**
     * 延时操作
     *
     * @param activity
     */
    private void jumpToLogin(final Activity activity) {
        addSubscribe(Flowable.timer(1000, TimeUnit.MILLISECONDS)
                .compose(RxUtil.<Long>rxSchedulerHelper())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        gotoLoginActivity(activity);
                    }
                })
        );
    }

    /**
     * 获取保存的用户登录信息
     *
     * @param context 上下文
     */
    @Override
    public void getAutoLoginInfo(Activity context) {

        gotoLoginActivity(context);

    }

    @Override
    public String getToken() {
        return mDataManager.getToken();
    }

    @Override
    public String getCarNo() {
        return mDataManager.getCarNo();
    }


    /**
     * 跳转到登录界面
     *
     * @param activity 上下文
     */
    private void gotoLoginActivity(Activity activity) {
        Intent intent = new Intent(activity, LoginActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        activity.finish();
    }


}
