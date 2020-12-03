package com.carnetwork.hansen.mvp.presenter.main;

import com.carnetwork.hansen.base.RxPresenter;
import com.carnetwork.hansen.mvp.contract.main.CarListContract;
import com.carnetwork.hansen.mvp.contract.main.MainContract;
import com.carnetwork.hansen.mvp.model.DataManager;

import javax.inject.Inject;

/**
 * @author HanN on 2020/12/2 10:08
 * @email: 1356548475@qq.com
 * @project carnetwork
 * @description:
 * @updateuser:
 * @updatedata: 2020/12/2 10:08
 * @updateremark:
 * @version: 2.1.67
 */
public class CarListPresenter extends RxPresenter<CarListContract.View> implements CarListContract.Presenter {
    private DataManager mDataManager;
    @Inject
    public CarListPresenter(DataManager mDataManager) {
        this.mDataManager = mDataManager;
    }
}
