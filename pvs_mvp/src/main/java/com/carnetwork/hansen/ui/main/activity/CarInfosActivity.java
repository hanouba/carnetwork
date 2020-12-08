package com.carnetwork.hansen.ui.main.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.base.BaseActivity;
import com.carnetwork.hansen.mvp.contract.main.CarInfoContract;
import com.carnetwork.hansen.mvp.model.bean.AllDrvier;
import com.carnetwork.hansen.mvp.presenter.main.DriverInfoPresenter;
import com.carnetwork.hansen.ui.main.adapter.DriverInfosAdapter;
import com.carnetwork.hansen.util.Music;
import com.carnetwork.hansen.util.MusicAdapter;
import com.carnetwork.hansen.util.MusicList;
import com.carnetwork.hansen.util.MusicService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 车辆信息显示
 */
public class CarInfosActivity extends BaseActivity<DriverInfoPresenter> implements CarInfoContract.View, View.OnClickListener {
    private RecyclerView recyclerView;
    private String carNo = "";
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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
    }
}
