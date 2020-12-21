package com.carnetwork.hansen.mvp.contract.main;

import android.app.Activity;

import com.carnetwork.hansen.base.BasePresenter;
import com.carnetwork.hansen.base.BaseView;
import com.tbruyelle.rxpermissions2.RxPermissions;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:
 */

public interface SplashContract {

    interface View extends BaseView {
    void showPointDialog();

    }

    interface  Presenter extends BasePresenter<View> {
        void checkPermissions(RxPermissions rxPermissions,Activity activity);
        void getAutoLoginInfo(Activity  activity);
        String getToken();
        String getCarNo();
    }
}
