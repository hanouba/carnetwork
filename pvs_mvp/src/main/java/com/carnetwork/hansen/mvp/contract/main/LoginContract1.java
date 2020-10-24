package com.carnetwork.hansen.mvp.contract.main;

import com.carnetwork.hansen.base.BasePresenter;
import com.carnetwork.hansen.base.BaseView;
import com.carnetwork.hansen.mvp.model.db.LoginInfo;

/**
 * remark:
 */
public interface LoginContract1 {

    interface View extends BaseView {

        void showErrorDialog(String msg);

//
        void gotoMainActivity();


    }

    interface Presenter extends BasePresenter<View> {

//
        LoginInfo getLoginInfo();

        boolean getRemberPsdState();

        void login(String carno,String carLicence,String phone,String name);


    }
}
