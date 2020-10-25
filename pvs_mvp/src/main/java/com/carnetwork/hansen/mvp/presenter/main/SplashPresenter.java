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
     * @param isAuto        是否自动登录
     */
    @Override
    public void checkPermissions(final RxPermissions rxPermissions, final Activity activity, final boolean isAuto) {
        addSubscribe(rxPermissions
                .request(PERMISSIONS)
                .subscribe(granted -> {
                    LogUtils.d("获取权限");
                    jumpToLogin(activity, isAuto);
                })
        );
    }

    /**
     * 延时操作
     *
     * @param activity
     * @param isAuto   是否需要自动登录
     */
    private void jumpToLogin(final Activity activity, final boolean isAuto) {
        addSubscribe(Flowable.timer(1000, TimeUnit.MILLISECONDS)
                        .compose(RxUtil.<Long>rxSchedulerHelper())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) {
                                if (isAuto) {
//                            自动（后台）登录
                                    getAutoLoginInfo(activity);
                                } else {
//                            跳转至登录界面
                                    gotoLoginActivity(activity);
                                }
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
        LoginInfo userInfo = mDataManager.getUserInfo();
        if (userInfo != null) {


        } else {
            gotoLoginActivity(context);
        }

    }


    /**
     * 获取自动登录的状态
     *
     * @return 是否需要自动登录
     */
    @Override
    public boolean getAgetAutoLoginState() {
//        return mDataManager.getAutoLoginState();
        return false;
    }

    /**
     * 自动登录（后台登录）
     *
     * @param username 用户名
     * @param password 密码
     * @param activity 上下文
     * @param ip       服务器IP
     * @param port     服务器端口
     */
    private void autoLogin(final String username, final String password, final Activity activity, final String ip, final String port) {
        Map<String, String> map = new HashMap();
        map.put("cmd", "CLIENT_USER_LOGIN");
        map.put("userid", username);
        map.put("lang", "cn");
        map.put("pwd", password);
        map.put("deviceType", "mobile");


    }

    /**
     * 跳转到主界面
     *
     * @param activity 上下文
     */
    private void gotoMainActivity(Activity activity) {
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        activity.finish();
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

    /**
     * 更改Retrofit中BaseUrl
     *
     * @param newUrl
     */
    public void changeBaseUrl(String newUrl) {
        //当你项目中只有一个 BaseUrl ,但需要动态改变,全局 BaseUrl 显得非常方便
        RetrofitUrlManager.getInstance().setGlobalDomain(newUrl);
        HttpUrl httpUrl = RetrofitUrlManager.getInstance().getGlobalDomain();
        if (null == httpUrl || !httpUrl.toString().equals(newUrl)) {
            RetrofitUrlManager.getInstance().setGlobalDomain(newUrl);
        }
    }
}
