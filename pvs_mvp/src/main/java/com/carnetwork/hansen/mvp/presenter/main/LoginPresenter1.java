package com.carnetwork.hansen.mvp.presenter.main;





import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.base.RxPresenter;
import com.carnetwork.hansen.mvp.contract.main.LoginContract1;
import com.carnetwork.hansen.mvp.model.DataManager;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.db.LoginInfo;
import com.carnetwork.hansen.mvp.model.http.api.MyApis;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;
import com.carnetwork.hansen.util.RxUtil;
import com.carnetwork.hansen.widget.CommonSubscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.carnetwork.hansen.app.Constants.KEY_START_TIME;


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
                        if (loginBean.isSuccess()) {
                            SPUtils.getInstance().put(Constants.TOKEN,loginBean.getModel());
                            mDataManager.setToken(loginBean.getModel());
                            mView.gotoMainActivity();
                        } else {
                            mView.loginFail(loginBean.getErrorMessage());
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

    @Override
    public void getMessageCode(String phone) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApis.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        MyApis request = retrofit.create(MyApis.class);

        SPUtils.getInstance().put(KEY_START_TIME,System.currentTimeMillis());
        Observable<MyHttpResponse> observable = request.getMessageCode(phone);

        observable.subscribeOn(Schedulers.io())   // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //切换到主线程 处理请求结果
                .subscribe(new Observer<MyHttpResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "logOn  onSubscribe");
                    }
                    @Override
                    public void onNext(MyHttpResponse myHttpResponse) {
                        if (!myHttpResponse.isSuccess()) {
                         //获取验证码成功
                            ToastUtils.showShort("验证码获取失败");
                        }else {
                            //获取成功
                            //读取短信验证码自动填写
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "logOn  onError");
                    }
                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "logOn  onComplete");
                    }
                });
    }


}
