package com.carnetwork.hansen.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.app.MyApplication;
import com.carnetwork.hansen.base.SimpleActivity;
import com.carnetwork.hansen.mvp.model.bean.ProjectEntity;
import com.carnetwork.hansen.widget.LineEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 创建新的车队
 */
public class CreatProject extends SimpleActivity {


    @BindView(R.id.iv_back)
    ImageButton ivBack;
    @BindView(R.id.info_title_name)
    TextView infoTitleName;

    @BindView(R.id.et_project_name)
    LineEditText etProjectName;


    @Override
    protected int getLayout() {
        return R.layout.activity_create_project;
    }

    @Override
    protected void initEventAndData() {
        infoTitleName.setText("创建车队");
    }

    @OnClick({R.id.iv_back, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_next:
                String motorCadeName  = etProjectName.getText().toString().trim();
                if (TextUtils.isEmpty(motorCadeName))  {
                    ToastUtils.showShort("请填写车队名称");
                    return;
                }

                Intent intent = new Intent(mContext, CreateProjectAddActivity.class);
                intent.putExtra(Constants.PROJECT_NAME, motorCadeName);
                startActivityForResult(intent, 1000);

                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                finish();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}