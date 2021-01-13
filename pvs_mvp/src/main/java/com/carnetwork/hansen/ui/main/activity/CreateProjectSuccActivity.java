package com.carnetwork.hansen.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.carnetwork.hansen.R;
import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.base.SimpleActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 成功
 */
public class CreateProjectSuccActivity extends SimpleActivity {


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
    @BindView(R.id.tv_tip_succ)
    TextView tvTipSucc;
    @BindView(R.id.project_name)
    TextView projectName;
    @BindView(R.id.tv_project_name)
    TextView tvProjectName;
    @BindView(R.id.project_username)
    TextView projectUsername;
    @BindView(R.id.tv_project_username)
    TextView tvProjectUsername;
    @BindView(R.id.tv_tip1)
    TextView tvTip1;
    @BindView(R.id.view_margin)
    View viewMargin;
    @BindView(R.id.added_user)
    TextView addedUser;
    @BindView(R.id.tv_added_user)
    TextView tvAddedUser;
    @BindView(R.id.tv_tip2)
    TextView tvTip2;
    @BindView(R.id.tv_tip3)
    TextView tvTip3;
    @BindView(R.id.bt_next)
    Button btNext;

    private String motoName;
    @Override
    protected int getLayout() {
        return R.layout.activity_create_project_succ;
    }

    @Override
    protected void initEventAndData() {
       motoName = getIntent().getStringExtra(Constants.PROJECT_NAME);
        String userPhone = getIntent().getStringExtra(Constants.USER_PHONE_NUM);
        tvProjectName.setText(motoName);
        tvProjectUsername.setText(userPhone);

        infoTitleName.setText("创建成功");
    }



    @OnClick({R.id.iv_back, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.bt_next:
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra("LOGIN_TAG", true);
                intent.putExtra(Constants.TEMP_PROJECT_NAME, motoName);
                intent.putExtra(Constants.TEMP_USER_PHONE_NUM, tvProjectUsername.getText().toString());
                startActivity(intent);
                break;
        }
    }
}