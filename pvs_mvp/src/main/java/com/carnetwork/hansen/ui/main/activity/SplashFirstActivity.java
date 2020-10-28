package com.carnetwork.hansen.ui.main.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.blankj.utilcode.util.LogUtils;

import java.util.Set;

public class SplashFirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Set<String> Categories = intent.getCategories();
        if (Categories != null) {
            for (String str : Categories) {
                LogUtils.e("category : " + str);
            }
        }
        if (!isTaskRoot()) {

            Intent mainIntent = getIntent();
            String action = mainIntent.getAction();
            if (mainIntent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finishAffinity();
            }
        } else {
            startActivity(new Intent(this, SplashActivity.class));
            finishAffinity();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return true;
    }
}
