package com.carnetwork.hansen.ui.main.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.base.BaseActivity;
import com.carnetwork.hansen.mvp.contract.main.CarListContract;
import com.carnetwork.hansen.mvp.model.bean.CarListBean;
import com.carnetwork.hansen.mvp.presenter.main.CarListPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 車輛列表
 * 選擇一個車輛
 * 或者創建一個新的車輛
 */
public class CarListActivity extends BaseActivity<CarListPresenter> implements CarListContract.View {


    @BindView(R.id.iv_add_new)
    ImageView ivAddNew;
    @BindView(R.id.rv_car_list)
    RecyclerView rvCarList;
    @BindView(R.id.bt_next)
    Button btNext;

    @Override
    protected int getLayout() {
        return R.layout.activity_car_list;
    }

    @Override
    protected void initEventAndData() {
        showProcessDialog("");

        mPresenter.getCarList("");
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }



    @OnClick({R.id.iv_add_new, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_add_new:
                //添加車輛

                break;
            case R.id.bt_next:
                //下一步 跳轉到主界面
                break;
        }
    }

    /**
     * 錯誤信息顯示 隱藏加載界面
     * @param carListBeans
     */
    @Override
    public void showCarList(List<CarListBean> carListBeans) {
        dismissProcessDialog();

    }

    @Override
    public void showErrorMsg(String msg) {

        dismissProcessDialog();
        ToastUtils.showShort(msg);
    }
}