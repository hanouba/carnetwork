package com.carnetwork.hansen.ui.main.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;


import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SPUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.base.BaseActivity;
import com.carnetwork.hansen.mvp.contract.main.SplashContract;
import com.carnetwork.hansen.mvp.presenter.main.SplashPresenter;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;

/**
 *
 */
public class SplashActivity extends BaseActivity<SplashPresenter> implements SplashContract.View {

    AlertDialog.Builder alertDialog;
    @BindView(R.id.iv_welcome_bg)
    ImageView iv_welcome_bg;

    private boolean agetAutoLoginState;

    @Override
    protected void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initEventAndData() {

        //设置状态栏颜色
//        StatusBarUtil.setTranslucent(SplashActivity.this, 0);
//        agetAutoLoginState = mPresenter.getAgetAutoLoginState();
//        if (agetAutoLoginState) {
//            Constants.NEW_UID = SPUtils.getInstance().getString(Constants.NEW_UID_KEY);
//            if (Constants.NEW_UID.equals("") || Constants.NEW_UID.isEmpty()) {
//                Intent intent = new Intent(this,LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }else {
//                mPresenter.checkPermissions(new RxPermissions(this),this , true);
//            }
//
//        }else {
//            mPresenter.checkPermissions(new RxPermissions(this),this , false);
//        }

        String token = mPresenter.getToken();
        SPUtils.getInstance().put(Constants.TOKEN, "");
        LogUtils.d("tokentokentoken" + token);
        if (!"".equals(token)) {
            SPUtils.getInstance().put(Constants.TOKEN, token);
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


            startActivity(intent);
        } else {
            mPresenter.checkPermissions(new RxPermissions(this), this, false);
        }

    }


    @Override
    public void showPointDialog() {
        alertDialog = new AlertDialog.Builder(this)
                .setTitle("权限缺失,会导致应用异常,是否获取")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mPresenter.checkPermissions(new RxPermissions(SplashActivity.this), SplashActivity.this, agetAutoLoginState);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplication(), LoginActivity.class);
                        getActivityComponent().getActivity().startActivity(intent);
                        finish();
                    }
                });
        alertDialog.show();

    }
}
