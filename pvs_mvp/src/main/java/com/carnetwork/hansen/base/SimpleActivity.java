package com.carnetwork.hansen.base;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;


import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.MyApplication;
import com.carnetwork.hansen.util.StatusBarUtil;
import com.carnetwork.hansen.widget.MineProcessDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * author：Sun on 2017/8/25/0025.
 * email：1564063766@qq.com
 * remark:无MVP的activity基类
 */
public abstract class SimpleActivity extends SupportActivity {

    protected Activity mContext;
    private Unbinder mUnBinder;
    protected MineProcessDialog mProcessDialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //屏幕长亮 无效
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(getLayout());
        mUnBinder = ButterKnife.bind(this);

        mContext = this;
//        RefWatcher refWatcher = MyApplication.getRefWatcher(this);//1
//        refWatcher.watch(this);
        onViewCreated();
        MyApplication.getInstance().addActivity(this);
        setStatusBar();

        initEventAndData();
//

    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);

    }

    protected  void setStatusBar() {
        StatusBarUtil.setColor(mContext, getResources().getColor(R.color.color_blue),0);
        StatusBarUtil.setLightMode(mContext);
    };



    protected void onViewCreated() {

    }





    @Override
    protected void onDestroy() {
        super.onDestroy();
        dismissProcessDialog();
        MyApplication.getInstance().removeActivity(this);
        mUnBinder.unbind();
    }

    protected abstract int getLayout();
    protected abstract void initEventAndData();

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }
    /**
     * 隐藏加载框
     */
    public void dismissProcessDialog() {
        if (mProcessDialog != null && mProcessDialog.isShowing()) {
            mProcessDialog.dismiss();
        }
    }

    /**
     * 显示加载框
     *
     * @param message 提示信息
     */
    protected void showProcessDialog(String message) {
        showProcessDialog(message, true, true);
    }

    /**
     * 显示加载框
     *
     * @param message       提示信息
     * @param cancelable    是否可取消
     * @param cancelOutside 点击 dialog 外部自动消失
     */
    public void showProcessDialog(String message, boolean cancelable, boolean cancelOutside) {
        if (isFinishing() || isDestroyed()) {
            return;
        }
        if (mProcessDialog != null && mProcessDialog.isShowing()) {
            mProcessDialog.setMessage(message);
            mProcessDialog.setCancelable(cancelable);
            mProcessDialog.setCanceledOnTouchOutside(cancelOutside);
            return;
        }
        mProcessDialog = new MineProcessDialog.Builder(this)
                .setMessage(message)
                .setCancelable(cancelable)
                .setCancelOutside(cancelOutside)
                .create();
        mProcessDialog.show();
    }

}
