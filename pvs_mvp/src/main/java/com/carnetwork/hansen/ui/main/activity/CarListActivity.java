package com.carnetwork.hansen.ui.main.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.carnetwork.hansen.R;
import com.carnetwork.hansen.base.BaseActivity;
import com.carnetwork.hansen.mvp.contract.main.CarListContract;
import com.carnetwork.hansen.mvp.presenter.main.CarListPresenter;

public class CarListActivity extends BaseActivity<CarListPresenter> implements CarListContract.View {



    @Override
    protected int getLayout() {
        return R.layout.activity_car_list;
    }

    @Override
    protected void initEventAndData() {

    }

    @Override
    protected void initInject() {

    }
}