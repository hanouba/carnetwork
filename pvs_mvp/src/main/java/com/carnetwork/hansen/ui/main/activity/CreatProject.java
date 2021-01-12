package com.carnetwork.hansen.ui.main.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.base.SimpleActivity;
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
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.iv_step)
    ImageView ivStep;
    @BindView(R.id.tv_create_step1)
    TextView tvCreateStep1;
    @BindView(R.id.tv_create_step1_tip)
    TextView tvCreateStep1Tip;
    @BindView(R.id.tv_create_step2)
    TextView tvCreateStep2;
    @BindView(R.id.tv_create_step2_tip)
    TextView tvCreateStep2Tip;
    @BindView(R.id.tv_create_step3)
    TextView tvCreateStep3;
    @BindView(R.id.tv_create_step3_tip)
    TextView tvCreateStep3Tip;
    @BindView(R.id.rl_step)
    RelativeLayout rlStep;
    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.et_project_name)
    LineEditText etProjectName;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.bt_next)
    Button btNext;

    @Override
    protected int getLayout() {
        return R.layout.activity_create_project;
    }

    @Override
    protected void initEventAndData() {

    }

    @OnClick({R.id.iv_back, R.id.et_project_name, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.et_project_name:
                break;
            case R.id.bt_next:
                String motorCadeName  = etProjectName.getText().toString().trim();
                if (TextUtils.isEmpty(motorCadeName))  {
                    ToastUtils.showShort("请填写车队名称");
                    return;
                }



                break;
        }
    }

    
}