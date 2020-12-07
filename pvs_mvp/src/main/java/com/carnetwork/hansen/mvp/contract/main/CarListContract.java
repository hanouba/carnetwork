package com.carnetwork.hansen.mvp.contract.main;

import com.carnetwork.hansen.base.BasePresenter;
import com.carnetwork.hansen.base.BaseView;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;
import com.carnetwork.hansen.mvp.model.bean.CarCreateEnity;
import com.carnetwork.hansen.mvp.model.bean.CarListBean;

import java.util.List;

/**
 * @author HanN on 2020/12/2 10:07
 * @email: 1356548475@qq.com
 * @project carnetwork
 * @description: 车辆信息
 * @updateuser:
 * @updatedata: 2020/12/2 10:07
 * @updateremark:
 * @version: 2.1.67
 */
public interface CarListContract {
    interface View extends BaseView {
        void showCarList(List<CarListBean> carListBeans);
        void showErrorMsg(String msg);
    }

    interface Presenter extends BasePresenter<CarListContract.View> {
        void getCarList(String token);
        void createCar(CarCreateEnity carCreateEnity);


    }
}
