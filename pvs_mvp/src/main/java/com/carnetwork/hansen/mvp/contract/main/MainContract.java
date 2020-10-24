package com.carnetwork.hansen.mvp.contract.main;

import android.content.Context;

import com.carnetwork.hansen.base.BasePresenter;
import com.carnetwork.hansen.base.BaseView;


/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:
 */
public interface MainContract {

    interface View extends BaseView {


        void resetLocation();
        void relogin();
    }

    interface Presenter extends BasePresenter<View> {


    }
}
