package com.carnetwork.hansen.mvp.contract.main;

import com.carnetwork.hansen.base.BasePresenter;
import com.carnetwork.hansen.base.BaseView;
import com.carnetwork.hansen.mvp.model.db.LoginInfo;

import java.util.List;

/**
 * remark:
 */
public interface LoginContract {

    interface View extends BaseView {

        void showErrorDialog(String msg);

        void gotoMainActivity(boolean firstLogin);

        void showStatusDialog(String tag);
    }

    interface Presenter extends BasePresenter<View> {
        void login(String name, String psd, String ip, String port);




    }
}
