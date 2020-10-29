package com.carnetwork.hansen.ui.main.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.SPUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.base.BaseActivity;
import com.carnetwork.hansen.mvp.contract.main.CarInfoContract;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;
import com.carnetwork.hansen.mvp.presenter.main.DriverInfoPresenter;
import com.carnetwork.hansen.ui.main.adapter.DriverInfosAdapter;

import java.util.List;

/**
 * 车辆信息显示
 */
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
    public void showAllDriver(List<AllDrvier> allCar) {

        driverInfosAdapter = new DriverInfosAdapter(allCar);
        recyclerView.setAdapter(driverInfosAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
         carNo = SPUtils.getInstance().getString(Constants.CAR_NO);
    }
}
