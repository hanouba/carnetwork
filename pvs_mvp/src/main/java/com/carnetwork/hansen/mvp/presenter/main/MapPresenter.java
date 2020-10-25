package com.carnetwork.hansen.mvp.presenter.main;

import android.util.Log;

import com.carnetwork.hansen.base.RxPresenter;
import com.carnetwork.hansen.mvp.contract.main.MainContract;
import com.carnetwork.hansen.mvp.contract.main.MapContract;
import com.carnetwork.hansen.mvp.model.DataManager;
import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.util.RxUtil;
import com.carnetwork.hansen.widget.CommonSubscriber;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

/**
 * @author HanN on 2020/10/23 10:07
 * @email: 1356548475@qq.com
 * @project carnetwork
 * @description:
 * @updateuser:
 * @updatedata: 2020/10/23 10:07
 * @updateremark:
 * @version: 2.1.67
 */
public class MapPresenter  extends RxPresenter<MapContract.View> implements MapContract.Presenter {

    private DataManager mDataManager;
    @Inject
    public MapPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }

    @Override
    public void getAllCar(String token, String carNo) {



        addSubscribe(mDataManager.getAllCar(carNo)
                .compose(RxUtil.<AllCar>rxSchedulerHelper())
                .subscribeWith(new CommonSubscriber<AllCar>(mView) {
                    @Override
                    public void onNext(AllCar allCar) {
                        Log.i("", "onNext: 获取到allCar"+allCar.toString());
                        mView.showAllCar(allCar);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        Log.i("", "onNext: 获取到onError"+e.toString());
                    }
                })
        );
    }
}
