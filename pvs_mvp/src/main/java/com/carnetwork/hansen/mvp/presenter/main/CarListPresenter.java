package com.carnetwork.hansen.mvp.presenter.main;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.base.RxPresenter;
import com.carnetwork.hansen.mvp.contract.main.CarListContract;
import com.carnetwork.hansen.mvp.contract.main.MainContract;
import com.carnetwork.hansen.mvp.model.DataManager;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;
import com.carnetwork.hansen.mvp.model.bean.CarCreateEnity;
import com.carnetwork.hansen.mvp.model.bean.CarListBean;
import com.carnetwork.hansen.mvp.model.bean.LoginBean;
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

        addSubscribe(mDataManager.getCarCanUse(token)
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

    @Override
    public void createCar(CarCreateEnity carCreateEnity) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApis.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        MyApis request = retrofit.create(MyApis.class);
        String token = SPUtils.getInstance().getString(Constants.TOKEN);

        Observable<MyHttpResponse> observable = request.createNewCar(carCreateEnity,token);

        observable.subscribeOn(Schedulers.io())   // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //切换到主线程 处理请求结果
                .subscribe(new Observer<MyHttpResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MyHttpResponse myHttpResponse) {
                        if (!myHttpResponse.isSuccess()) {
                            //创建车辆失败
                            //关闭弹窗
                            mView.showErrorMsg(myHttpResponse.getErrorMessage());

                        } else {
                            //创建车辆成功
                            //关闭弹窗
                            //刷新界面
                            mView.update();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    @Override
    public void selectCar(String carNo) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApis.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        MyApis request = retrofit.create(MyApis.class);
        String token = SPUtils.getInstance().getString(Constants.TOKEN);

        Observable<MyHttpResponse> observable = request.selectCar(carNo,token);

        observable.subscribeOn(Schedulers.io())   // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  //切换到主线程 处理请求结果
                .subscribe(new Observer<MyHttpResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MyHttpResponse myHttpResponse) {
                        if (!myHttpResponse.isSuccess()) {
                            //选择车辆失败
                            //不可以跳转过去
                            mView.showErrorMsg(myHttpResponse.getErrorMessage());

                        } else {
                            //选择车辆成功
                            //跳转到主界面
                            //
                            mView.toMainActivity();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

}
