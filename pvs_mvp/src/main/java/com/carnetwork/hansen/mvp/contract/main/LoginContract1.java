package com.carnetwork.hansen.mvp.contract.main;

import com.carnetwork.hansen.base.BasePresenter;
import com.carnetwork.hansen.base.BaseView;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.bean.ProjectEntity;
import com.carnetwork.hansen.mvp.model.db.LoginInfo;

import java.util.List;

/**
 * remark:
 */
public interface LoginContract1 {

    interface View extends BaseView {

        void showErrorDialog(String msg);

//
        void gotoMainActivity();
        void gotoCreateProject();
        void changeToCreateProject(String errorMsg);
        void showToLogin();
        void loginFail(String msg);


    }

    interface Presenter extends BasePresenter<View> {

//
        List<LoginInfo> getLoginInfo();

        void inserLoginInfo(LoginInfo loginInfo);

        void login(LoginEntity loginEntity);
        void login2(LoginEntity loginEntity);
        void createProject(ProjectEntity projectEntity);
        //获取验证码
        void getMessageCode(String phone);
    }
}
