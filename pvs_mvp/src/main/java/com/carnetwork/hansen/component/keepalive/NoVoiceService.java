package com.carnetwork.hansen.component.keepalive;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;

import com.blankj.utilcode.util.LogUtils;
import com.carnetwork.hansen.R;


/**
 * 创建者 by ${HanSir} on 2017/11/13.
 * 版权所有  WELLTRANS.
 * 说明     后台服务
 */


public class NoVoiceService extends Service implements MediaPlayer.OnCompletionListener{
    private boolean mPausePlay = false;//控制是否播放音频
    private MediaPlayer mediaPlayer;
    private Handler mHandler = new Handler();

    @SuppressLint("NewApi")
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * START_NOT_STICKY：当Service因为内存不足而被系统kill后，接下来未来的某个时间内，即使系统内存足够可用，系统也不会尝试重新创建此Service。
     * 除非程序中Client明确再次调用startService(...)启动此Service。
     * <p>
     * START_STICKY：当Service因为内存不足而被系统kill后，接下来未来的某个时间内，当系统内存足够可用的情况下，系统将会尝试重新创建此Service，
     * 一旦创建成功后将回调onStartCommand(...)方法，但其中的Intent将是null，pendingintent除外。
     * <p>
     * START_REDELIVER_INTENT：与START_STICKY唯一不同的是，回调onStartCommand(...)方法时，
     * 其中的Intent将是非空，将是最后一次调用startService(...)中的intent。
     *
     * @param intent
     * @param flags
     * @param startId
     * @return
     */

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(this, R.raw.no_voice);
            mediaPlayer.setVolume(0f, 0f);
            mediaPlayer.setOnCompletionListener(this);
        }
        play();
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 播放音频
     * 亮屏：播放保活
     * 锁屏：已连接，播音乐；未连接，不播放
     */
    private void play() {
        Intent intent = new Intent();
        intent.setAction("com.carnetwork.hansen.component.keepalive.NoVoiceService");
        sendBroadcast(intent);
        LogUtils.i("playmediaPlayer");
        if (mediaPlayer != null && !mediaPlayer.isPlaying() && !mPausePlay) {
            mediaPlayer.start();
        }
    }

    /**
     * 停止播放
     */
    private void pause() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
        mPausePlay = true;
    }

    //播放完成
    @Override
    public void onCompletion(MediaPlayer mp) {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                play();
            }
        }, 60 * 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
