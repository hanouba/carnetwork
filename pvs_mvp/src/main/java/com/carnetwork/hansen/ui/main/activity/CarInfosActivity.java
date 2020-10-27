package com.carnetwork.hansen.ui.main.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.base.BaseActivity;
import com.carnetwork.hansen.base.SimpleActivity;
import com.carnetwork.hansen.mvp.contract.main.CarInfoContract;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;
import com.carnetwork.hansen.mvp.presenter.main.DriverInfoPresenter;
import com.carnetwork.hansen.ui.main.adapter.DriverInfosAdapter;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CarInfosActivity extends BaseActivity<DriverInfoPresenter> implements CarInfoContract.View {
    private RecyclerView recyclerView;
    private String carNo ="";
    private DriverInfosAdapter driverInfosAdapter;
    @Override
    protected int getLayout() {
        return R.layout.activity_car_infos;
    }
    @Override
    protected void initEventAndData() {
        recyclerView = findViewById(R.id.recyclerView);
         carNo = SPUtils.getInstance().getString(Constants.CAR_NO);

        mPresenter.getAllDriver("", carNo);


        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

    }
    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }
    @Override
    public void showAllDriver(AllDrvier allCar) {
        driverInfosAdapter = new DriverInfosAdapter(allCar.getModel());
        recyclerView.setAdapter(driverInfosAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
         carNo = SPUtils.getInstance().getString(Constants.CAR_NO);
    }
}
