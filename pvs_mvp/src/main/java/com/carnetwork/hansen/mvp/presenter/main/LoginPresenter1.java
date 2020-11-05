package com.carnetwork.hansen.mvp.presenter.main;





import com.blankj.utilcode.util.SPUtils;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.base.RxPresenter;
import com.carnetwork.hansen.mvp.contract.main.LoginContract1;
import com.carnetwork.hansen.mvp.model.DataManager;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.db.LoginInfo;
import com.carnetwork.hansen.util.RxUtil;
import com.carnetwork.hansen.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;


public class LoginPresenter1 extends RxPresenter<LoginContract1.View> implements LoginContract1.Presenter {
    private String TAG = "LoginPresenter1";
    private DataManager mDataManager;

    @Inject
    public LoginPresenter1(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }


    @Override
    public List<LoginInfo> getLoginInfo() {

        return mDataManager.loadLoginUserInfo();
    }

    @Override
    public void inserLoginInfo(LoginInfo loginInfo) {
        mDataManager.insertLoginUserInfo(loginInfo);

    }



    @Override
    public void login(LoginEntity loginEntity) {


        addSubscribe(mDataManager.getLogin(loginEntity)
                .compose(RxUtil.<LoginBean>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<LoginBean>(mView) {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        if (loginBean.isSuccess().equals("true")) {
                            SPUtils.getInstance().put(Constants.TOKEN,loginBean.getModel());
                            mDataManager.setToken(loginBean.getModel());

                            mView.gotoMainActivity();
                        } else {
                            mView.loginFail("服务器错误");
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.loginFail("网络错误");
                    }
                })
        );


    }


}
