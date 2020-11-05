package com.carnetwork.hansen.mvp.contract.main;

import android.content.Context;

import com.carnetwork.hansen.base.BasePresenter;
import com.carnetwork.hansen.base.BaseView;
import com.carnetwork.hansen.mvp.model.bean.UploadMapEntity;


/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:
 */
public interface MainContract {

    interface View extends BaseView {


        void logOutSuccess();
        void showWrong(String msg);
    }

    interface Presenter extends BasePresenter<View> {


        void logout(String carNo);

        void setWorkState(boolean state);
        boolean getWorkState();
        //下班
        void logOff(String carNo,String token);
        void logOn(String carNo,String token);

    }
}
