package com.carnetwork.hansen.mvp.presenter.main;


import android.content.Context;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.UploadMapEntity;
import com.carnetwork.hansen.mvp.model.http.api.MyApis;
import com.google.gson.Gson;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.base.RxPresenter;
import com.carnetwork.hansen.component.RxBus;
import com.carnetwork.hansen.mvp.contract.main.MainContract;
import com.carnetwork.hansen.mvp.model.DataManager;
import com.carnetwork.hansen.mvp.model.db.UserLocationInfo;
import com.carnetwork.hansen.mvp.model.event.CommonEvent;
import com.carnetwork.hansen.mvp.model.event.EventCode;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;
import com.carnetwork.hansen.util.RxUtil;
import com.carnetwork.hansen.widget.CommonSubscriber;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by codeest on 16/8/9.
 */

public class MainPresenter extends RxPresenter<MainContract.View> implements MainContract.Presenter {

    private DataManager mDataManager;
    private String TAG = "MainPresenter";


    //    记录用户名
    private String username;

    //    上次上传时间
    private long lastUpdateTime = 0;

    //    上次记录时间
    private long lastRecordTime = 0;

    //    控制上传时的顺序
    private final String sync_upload = "sync_upload";

    //工作状态 是否提交定位信息
    private boolean workState;
    @Inject
    public MainPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void attachView(MainContract.View view) {
        super.attachView(view);
        registerEvent();
    }

    private void registerEvent() {
        addSubscribe(RxBus.getDefault().toFlowable(CommonEvent.class)
                        .compose(RxUtil.<CommonEvent>rxSchedulerHelper())
                        .subscribeWith(new CommonSubscriber<CommonEvent>(mView, null) {

                            @Override
                            public void onNext(CommonEvent commonevent) {
                                switch (commonevent.getCode()) {

                                    case EventCode.WORK_STATE:
                                        workState = commonevent.isTemp_boolean();

                                        break;
                                    default:

                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                super.onError(e);
                                registerEvent();
                            }
                        })
        );
    }




    @Override
    public void logout(String carNo) {
        mDataManager.setToken("");
        mDataManager.setCarNo("");
        SPUtils.getInstance().put(Constants.TOKEN,"");
        SPUtils.getInstance().put(Constants.IS_ON_WORK, false);
        addSubscribe(mDataManager.logout(carNo)
                .compose(RxUtil.<MyHttpResponse>rxSchedulerHelper())

                .subscribeWith(new CommonSubscriber<MyHttpResponse>(mView) {
                    @Override
                    public void onNext(MyHttpResponse httpResponse) {


                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mDataManager.setToken("");

                    }
                })
        );

        mView.logOutSuccess();
    }

    @Override
    public void setWorkState(boolean state) {
        mDataManager.setWorkState(state);
    }

    @Override
    public boolean getWorkState() {
        return mDataManager.getWorkState();
    }

    @Override
    public void logOff(String carNo,String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApis.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        MyApis request = retrofit.create(MyApis.class);


        Observable<MyHttpResponse> observable = request.logoff(carNo,token);

        observable.subscribeOn(Schedulers.io())   // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //切换到主线程 处理请求结果
                .subscribe(new Observer<MyHttpResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "logOff  onSubscribe");
                    }
                    @Override
                    public void onNext(MyHttpResponse myHttpResponse) {
                        if (myHttpResponse.isSuccess()) {
                            LogUtils.d(TAG, "logOff  onNext");
                        }else {
                            mView.showWrong(myHttpResponse.getErrorMessage());
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        LogUtils.d(TAG, "logOff  onError");
                    }
                    @Override
                    public void onComplete() {
                        LogUtils.d(TAG, "logOff  onComplete");
                    }
                });
    }

    @Override
    public void logOn(String carNo,String token) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApis.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        MyApis request = retrofit.create(MyApis.class);


        Observable<MyHttpResponse> observable = request.logon(carNo,token);

        observable.subscribeOn(Schedulers.io())   // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //切换到主线程 处理请求结果
                .subscribe(new Observer<MyHttpResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(TAG, "logOn  onSubscribe");
                    }
                    @Override
                    public void onNext(MyHttpResponse myHttpResponse) {
                        if (myHttpResponse.isSuccess()) {
                            LogUtils.d(TAG, "logOn  onNext");
                        }else {
                            mView.showWrong(myHttpResponse.getErrorMessage());
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
