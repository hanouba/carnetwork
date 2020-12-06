package com.carnetwork.hansen.mvp.presenter.main;

import com.carnetwork.hansen.base.RxPresenter;
import com.carnetwork.hansen.mvp.contract.main.CarListContract;
import com.carnetwork.hansen.mvp.contract.main.MainContract;
import com.carnetwork.hansen.mvp.model.DataManager;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;
import com.carnetwork.hansen.mvp.model.bean.CarListBean;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
import com.carnetwork.hansen.mvp.model.http.response.MyHttpResponse;
import com.carnetwork.hansen.util.RxUtil;
import com.carnetwork.hansen.widget.CommonSubscriber;

import java.util.List;

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

    @Override
    public void getCarList(String token) {
        addSubscribe(mDataManager.getCarCanUse()
                .compose(RxUtil.<MyHttpResponse<List<CarListBean>>>rxSchedulerHelper())
                .compose(RxUtil.<List<CarListBean>>handleMyResult())
                .subscribeWith(new CommonSubscriber<List<CarListBean>>(mView) {
                    @Override
                    public void onNext(List<CarListBean> sateBeans) {
                        mView.showCarList(sateBeans);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        mView.showErrorMsg(e.getMessage());
                    }
                })
        );
    }
}
