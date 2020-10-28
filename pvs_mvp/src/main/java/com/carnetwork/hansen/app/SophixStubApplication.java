package com.carnetwork.hansen.app;

import android.content.Context;
import androidx.annotation.Keep;
import androidx.multidex.MultiDex;
import android.util.Log;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;
import com.taobao.sophix.listener.PatchLoadStatusListener;

/**
 * Created by Administrator on 2017/12/12.
 */

public class SophixStubApplication extends SophixApplication {
    private final String TAG = "SophixStubApplication";

    @Keep
    @SophixEntry(MyApplication.class)
    static class RealApplicationStub {
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
        initSophix();
        //热修复拉取补丁

    }

    @Override
    public void onCreate() {
        super.onCreate();
        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，
        // 建议放在后面任意时刻，如onCreate中
        SophixManager.getInstance().queryAndLoadNewPatch();
    }

    private void initSophix() {
        String appVersion = "1.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {

        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this)
                .setAppVersion(appVersion)
                .setSecretMetaData("24861045-1","3c0916804b9e0424369afaa7adb43b55","MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCorWvi199XAjNBqIEy88A//tYLQLHc+ZZDs2JKreEb/T3cImPMQkXjFPRQlsmjg02F0Lyvu+O+xJcpR0WNJF3d/kxDWYv3/49dB59h23AgVVkUWS6OSNrSbcPwzrMX4nQ7Xl4vZOmF2mlGrD5gIWuMZ+2hPR/pc+ZsEJsPThWN1C81r7Uh6sxnGastNJYxuRPETAkeabH0+p2FVAAzeSKpq0exVrcxddUEGg2TNYrt4Ol0PRT9lHPhFvqf6l6PnAMsCc0bVnz26igmTIld59J9cyatFTBnN3l8MVWa+wWAAe5OMwkgBKKkKmQNLXMmzC7pmDrrD/6stp3kpcI/vPfrAgMBAAECggEABTOb4RnxHDjIJd0xZsXXhuGv7hbnpT3ZIWtVu6HTEE7l6nTxCOp+RzttMVMFEtLIwDioqO6tXkOnubEl9u1JK8Bcz7LiOxqZbDAA/T6MHaMkJRl+8wpSHS0fXryxDyp/lRka5HFDNkp+9pv3LHW7YFAQJunAoy1czLZSYc1xorb8hBW1MgZMv2LdzkczF+5pSxrz382GUgL6jbBAfjARFoJKQgsPlp+gbOypX2ernWjaM8Z61y5Mq3OV05NY6XUM34NNio1GxtlC0lrVNTDGR+10FZtQjVAiLdljKUBK58MN7m0tom/w/wUh9f/f//Zkj4NJBkTG5E6nSnTQfKoU8QKBgQDi28EuXXLz4KzAi7NeZc2RRBKDme5arYk8P03g8NvwoMudOv4dwTVYYHpSUjABGiQR5ajY4xBHXlioXowuluJSspo38ydJy4+0e1ecpXxDAqbqiE/JsA7e46pVOK/FyFNsZnUkgp/zFOZQx3OlJcfBdGLI8bu3Sgib8dhBaT1+IwKBgQC+WGIj/6/c3ySwuXHYBHo9BTJuPb/akWFZFQb8Xspht2CEzJFk6RFcA+HQx4ooyavq26J4cQOfbpIUCj6Vtlrl/AKjvg7bf3UYIlLXsLIOUGWheNRUA/XfohUft5ytiJDHYtnhHePvJyB7wiLw29pu/WYEttpGfPU/Pih1oVnnmQKBgD4QtVeEBZEnZNwA3H/1Bjq6Dv6mPInedG9hOmNSQjohI9POcfueAoQ9UPn4tl1202t6E6nS5X9BFqxpwZx1d1H8vyiTg6oy4QwVurCGrep/bT85L+sUGd1tGArcyWopFnnPxjjI7A3LJ//tsXv7TB1nRCeJyzuoK5bOocWzjzlVAoGAE+IrrlMBTfNJY+SbTdN56XHniLiOmAhjwOh/aFGGlpqPTJAKCB6In7IQHr0ZY5VdjhUPbcFwfXERdWJ0kzMPX8Lpi61I5Aj2dGTxfnKIxxIC69ExbO1U+910k3Gcvkvrw8hBtlBnBQ5zSDpJaBuvP7TcuVVYi9B11ftqQo9uUtECgYEAxO5guq07CS9Ez1vkuC0RrplbHtQuqIpdqztaBncAHIg66fXreR0l3zjuVwcuycYwBII2peeH1LvDxA7JZ+vjaxTmWXf/wfJ8eA8udi6iKS5jZu6sURXiXxuD3ENBuBw0DkCFRr1o/Q0dQ/FOxjgzf2385A+SuoTVUFo4EVBfMQk=")
                .setEnableDebug(true)
                .setPatchLoadStatusStub(new PatchLoadStatusListener() {
                    @Override
                    public void onLoad(final int mode, final int code, final String info, final int handlePatchVersion) {
                        // 补丁加载回调通知
                        if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                            // 表明补丁加载成功
                            Log.e(TAG, "表明补丁加载成功 : " + PatchStatus.CODE_LOAD_SUCCESS);
                        } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                            // 表明新补丁生效需要重启. 开发者可提示用户或者强制重启;
                            Log.e(TAG, "表明新补丁生效需要重启 : " + PatchStatus.CODE_LOAD_RELAUNCH);
//                            RxBus.getDefault().post(new CommonEvent(EventCode.HOT_SOPHIX));
                        } else if (code == PatchStatus.CODE_REQ_NOUPDATE) {
                            Log.e(TAG, "没有可用的补丁 : " + code);
                        } else if (code == PatchStatus.CODE_REQ_CLEARPATCH) {
                            Log.e(TAG, "一键清除补丁 : " + code);
                        } else if (code == PatchStatus.CODE_LOAD_FAIL) {
                            Log.e(TAG, "补丁加载失败 : " + code);
                        } else {
                            // 其它错误信息
                            Log.e(TAG, "其它信息 : " + code);
                        }
                    }
                }).initialize();
    }


}
