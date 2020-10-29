package com.carnetwork.hansen.mvp.contract.main;

import com.carnetwork.hansen.base.BasePresenter;
import com.carnetwork.hansen.base.BaseView;
import com.carnetwork.hansen.mvp.model.bean.AllCar;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;

import java.util.List;

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
public interface CarInfoContract {
    interface View extends BaseView {
        void showAllDriver(List<AllDrvier> allCar);
    }

    interface Presenter extends BasePresenter<CarInfoContract.View> {
        void getAllDriver(String token, String carNo);
    }
}
