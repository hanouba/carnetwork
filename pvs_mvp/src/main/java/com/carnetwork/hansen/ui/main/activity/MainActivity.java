package com.carnetwork.hansen.ui.main.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.PersistableBundle;
import android.os.PowerManager;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import butterknife.BindView;

import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.app.MyApplication;
import com.carnetwork.hansen.base.BaseActivity;
import com.carnetwork.hansen.component.RxBus;
import com.carnetwork.hansen.component.keepalive.NoVoiceService;
import com.carnetwork.hansen.mvp.contract.main.MainContract;
import com.carnetwork.hansen.mvp.model.event.CommonEvent;
import com.carnetwork.hansen.mvp.model.event.EventCode;
import com.carnetwork.hansen.mvp.presenter.main.MainPresenter;
import com.carnetwork.hansen.ui.main.fragment.MapFragment;
import com.carnetwork.service.MusicService;
import com.carnetwork.hansen.util.StatusBarUtil;
import com.carnetwork.hansen.widget.SwitchButton;


/**
 * Created by codeest on 16/8/9.
 */

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View, View.OnClickListener {

    private MusicService musicService;


    private TextView tvCarNo, tvCarLicence, tvUserName, tvUserPhone;
    private String userName, userPhone, carNo, carLicence;
    private Button carInfos;
    private PowerManager.WakeLock mwl;
    private boolean workState;
    private String token;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @SuppressLint("InvalidWakeLockTag")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mwl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "MyTag");
        mwl.acquire();//屏幕关闭后保持活动

    }


    ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MusicBinder) service).getService();
            handler.sendEmptyMessage(0x01);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0x01) {
                handler.sendEmptyMessage(0x01);
            }
        }
    };

    @Override
    protected void initEventAndData() {
        //工作状态设置
        workState = SPUtils.getInstance().getBoolean(Constants.IS_ON_WORK);
        token = SPUtils.getInstance().getString(Constants.TOKEN);
        //获取用户信息
        userName = SPUtils.getInstance().getString(Constants.CAR_NAME);
        userPhone = SPUtils.getInstance().getString(Constants.CAR_PHONE);
        carNo = SPUtils.getInstance().getString(Constants.CAR_NO);
        carLicence = SPUtils.getInstance().getString(Constants.CAR_LICENCE);
        int roleType = SPUtils.getInstance().getInt(Constants.USER_ROLE_TYPE);
        initView();


        //设置用户信息
        tvCarNo.setText(carNo);
        tvCarLicence.setText(carLicence);
        tvUserName.setText(userName);
        tvUserPhone.setText(userPhone);
        if (roleType == 1) {
            //管理者
            carInfos.setVisibility(View.VISIBLE);
        }else {
            //司机
            carInfos.setVisibility(View.GONE);
        }
        carInfos.setOnClickListener(this);


        //        启动后台服务
        startService(new Intent(this, NoVoiceService.class));
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.carnetwork.hansen.component.keepalive.NoVoiceService");
        //        Intent intent = new Intent(this, MusicService.class);
        //        startService(intent);
        //        bindService(intent, conn, BIND_AUTO_CREATE);
    }

    private void initView() {
        replaceFragment(new MapFragment(), R.id.fl_main_content);
        //  获取控件
        tvCarNo = findViewById(R.id.tv_car_no);
        tvCarLicence = findViewById(R.id.tv_car_licence);
        tvUserName = findViewById(R.id.tv_user_name);
        tvUserPhone = findViewById(R.id.tv_user_phone);
        Button logOut = findViewById(R.id.bt_logout);
        DrawerLayout drawerLayout = findViewById(R.id.view_main);


        SwitchButton switchButton = findViewById(R.id.switch_button);
        carInfos = findViewById(R.id.bt_opencarinfo);
        //屏幕长亮
        drawerLayout.setKeepScreenOn(true);
        //退出
        logOut.setOnClickListener(this::onClick);
        switchButton.setChecked(workState);
        if (workState) {
            mPresenter.logOn(carNo, token);
        } else {

        }
        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SPUtils.getInstance().put(Constants.IS_ON_WORK, isChecked);
                RxBus.getDefault().post(new CommonEvent(EventCode.WORK_STATE, isChecked));
                if (isChecked) {
                    mPresenter.logOn(carNo, token);
                } else {
                    mPresenter.logOff(carNo, token);
                }
            }
        });
    }

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }


    @Override
    public void logOutSuccess() {
        try {
            Thread.sleep(500);
            MyApplication.getInstance().exitApp();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void showWrong(String msg) {

    }


    @Override
    protected void onResume() {
        super.onResume();

    }




    @Override
    public void onBackPressedSupport() {
        showExitDialog();
    }

    private void showExitDialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setMessage("确定退出吗");
        builder.setNegativeButton("取消", null);
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                MyApplication.getInstance().exitApp();


            }
        });
        builder.show();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        handler.removeCallbacksAndMessages(null);

    }


    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);
    }

    private String selectDate = "";

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_logout:

                mPresenter.logout(carNo);
                break;
            case R.id.bt_opencarinfo:
                mContext.startActivity(new Intent(mContext, CarInfosActivity.class));

                break;
            default:
        }
    }


    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            //与上次点击返回键时刻作差
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                //大于2000ms则认为是误操作，使用Toast进行提示
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                //并记录下本次点击“返回键”的时刻，以便下次进行判断
                mExitTime = System.currentTimeMillis();
            } else {
                //小于2000ms则认为是用户确实希望退出程序-调用System.exit()方法进行退出
                finish();
            }
            return true;
        }
        //继续执行父类其他点击事件
        return super.onKeyUp(keyCode, event);
    }
    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断用户是否点击了“返回键”

        return super.onKeyDown(keyCode, event);
    }

}
