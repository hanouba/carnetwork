package com.carnetwork.hansen.mvp.presenter.main;


import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.base.RxPresenter;
import com.carnetwork.hansen.mvp.contract.main.LoginContract1;
import com.carnetwork.hansen.mvp.model.DataManager;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.bean.LoginEntity;
import com.carnetwork.hansen.mvp.model.bean.ProjectEntity;
import com.carnetwork.hansen.mvp.model.bean.SateBean;
import com.carnetwork.hansen.mvp.model.db.LoginInfo;
import com.carnetwork.hansen.mvp.model.http.api.MyApis;
import com.carnetwork.hansen.mvp.model.http.exception.ApiException;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;
import com.carnetwork.hansen.util.RxUtil;
import com.carnetwork.hansen.widget.CommonSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
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

        addSubscribe(mDataManager.getLoginV2(loginEntity)
                .compose(RxUtil.<LoginBean>rxSchedulerHelper())

                .subscribeWith(new CommonSubscriber<LoginBean>(mView) {
                    @Override
                    public void onNext(LoginBean sateBeans) {

                        if (sateBeans.isSuccess()) {
                            //登录到主页

                            mView.gotoMainActivity(sateBeans);

                        } else {
                            //切换到创建项目界面
                            if ("CAR100004".equals(sateBeans.getErrorCode())) {
                                mView.showErrorDialog(sateBeans.getErrorMessage());
                            }else {
                                mView.changeToCreateProject(sateBeans.getErrorMessage());
                            }

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                        mView.showErrorDialog("");
                    }
                })
        );
//
//        addSubscribe(mDataManager.getLoginV2(loginEntity)
//                .subscribeOn(Schedulers.io())
//                .observeOn(Schedulers.io())
//                .flatMap((Function<LoginBean, Flowable<MyHttpResponse>>) eventStatusResultBean -> {
//                    if (eventStatusResultBean.isSuccess()){
//                        //登录到主页
//                        LogUtils.d("登录主要创建成功");
//                        return null;
//                    }else {
//                        //创建车队
//                        LogUtils.d("创建车队");
//                        return mDataManager.createProject(projectEntity);
//                    }
//
//
//                })
//                .compose(RxUtil.rxSchedulerHelper())
//                .subscribe(myHttpResponse -> {
//                    if (myHttpResponse.isSuccess()) {
//                        //创建成功
//                        LogUtils.d("创建车队创建成功");
//
//                    }else {
//                        //创建失败
//                        LogUtils.d("创建车队创建失败"+myHttpResponse.getErrorMessage());
//                    }
//                }, e -> {
//                    LogUtils.d("创建车队创建失败getMessage");
//                    LogUtils.d(e.getMessage());
//                })
//        );


//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(MyApis.HOST)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//
//        MyApis request = retrofit.create(MyApis.class);
//
//        Observable<MyHttpResponse> loginObservable = request.loginV3(loginEntity);
//        Observable<MyHttpResponse> projectObservable = request.createProject2(projectEntity);
//
//        loginObservable.subscribeOn(Schedulers.io())               // （初始被观察者）切换到IO线程进行网络请求1
//                .observeOn(AndroidSchedulers.mainThread())  // （新观察者）切换到主线程 处理网络请求1的结果
//                .doOnNext(new Consumer<MyHttpResponse>() {
//                    @Override
//                    public void accept(MyHttpResponse result) throws Exception {
//                        Log.d(TAG, "第1次网络请求成功"+result.isSuccess());
//                        boolean success = result.isSuccess();
//                        // 对第1次网络请求返回的结果进行操作 = 显示翻译结果
//                    }
//                })
//
//                .observeOn(Schedulers.io())                 // （新被观察者，同时也是新观察者）切换到IO线程去发起登录请求
//                // 特别注意：因为flatMap是对初始被观察者作变换，所以对于旧被观察者，它是新观察者，所以通过observeOn切换线程
//                // 但对于初始观察者，它则是新的被观察者
//                .flatMap(new Function<MyHttpResponse, ObservableSource<MyHttpResponse>>() { // 作变换，即作嵌套网络请求
//                    @Override
//                    public ObservableSource<MyHttpResponse> apply(MyHttpResponse result) throws Exception {
//                        // 将网络请求1转换成网络请求2，即发送网络请求2
//                        return projectObservable;
//                    }
//                })
//
//                .observeOn(AndroidSchedulers.mainThread())  // （初始观察者）切换到主线程 处理网络请求2的结果
//                .subscribe(new Consumer<MyHttpResponse>() {
//                    @Override
//                    public void accept(MyHttpResponse result) throws Exception {
//                        Log.d(TAG, "第2次网络请求成功"+result.isSuccess());
//
//                        // 对第2次网络请求返回的结果进行操作 = 显示翻译结果
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        System.out.println("登录失败");
//                    }
//                });


    }

    @Override
    public void login2(LoginEntity loginEntity) {
        addSubscribe(mDataManager.getLoginV2(loginEntity)
                .compose(RxUtil.<LoginBean>rxSchedulerHelper())

                .subscribeWith(new CommonSubscriber<LoginBean>(mView) {
                    @Override
                    public void onNext(LoginBean sateBeans) {

                        if (sateBeans.isSuccess()) {
                            //登录到主页

                            mView.gotoMainActivity(sateBeans);

                        } else {
                            //再次失败 提示错误信息
                            mView.loginFail(sateBeans.getErrorMessage());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                        mView.showErrorDialog("" + e.getMessage());
                    }
                })
        );
    }

    @Override
    public void createProject(ProjectEntity projectEntity) {
        addSubscribe(mDataManager.createProject(projectEntity)
                .compose(RxUtil.<MyHttpResponse>rxSchedulerHelper())

                .subscribeWith(new CommonSubscriber<MyHttpResponse>(mView) {
                    @Override
                    public void onNext(MyHttpResponse sateBeans) {

                        if (sateBeans.isSuccess()) {
                            // 提示创建成功 跳转到登录界面
                            //信息显示在登录界面
                            mView.showToLogin();
                        } else {
                            //创建车队失败提示信息
                            mView.showErrorDialog(sateBeans.getErrorMessage());

                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showErrorDialog("" + e.getMessage());
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

        SPUtils.getInstance().put(KEY_START_TIME, System.currentTimeMillis());
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
                        } else {
                            //获取成功
                            //读取短信验证码自动填写
                            mView.updataVerifi();
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

    @Override
    public void setToken(String token) {
        mDataManager.setToken(token);
        SPUtils.getInstance().put(Constants.TOKEN,token);
    }


}
