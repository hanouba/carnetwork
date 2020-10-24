package com.carnetwork.hansen.mvp.presenter.main;


import android.content.Context;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
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

import javax.inject.Inject;


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

                                    case EventCode.ERROR_RELOGIN:
                                        mView.relogin();
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






}
