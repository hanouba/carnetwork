package com.carnetwork.hansen.component.keepalive;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;

import com.blankj.utilcode.util.LogUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.ui.main.activity.MainActivity;


/**
 * 创建者 by ${HanSir} on 2017/11/13.
 * 版权所有  WELLTRANS.
 * 说明     后台服务
 */


public class NoVoiceService extends Service implements MediaPlayer.OnCompletionListener {
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
        Notification.Builder builder = new Notification.Builder(this.getApplicationContext()); //获取一个Notification构造器
        Intent nfIntent = new Intent(this, MainActivity.class);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, nfIntent, 0))
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_launcher)) // 设置下拉列表中的图标(大图标)
                .setContentTitle("车联APP正在运行") // 设置下拉列表里的标题
                .setSmallIcon(R.mipmap.ic_launcher) // 设置状态栏内的小图标
                .setContentText("正在获取定位") // 设置上下文内容
                .setWhen(System.currentTimeMillis()); // 设置该通知发生的时间

        Notification notification = builder.build(); // 获取构建好的Notification
        notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音

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
            LogUtils.i("playmediaPlayer==mediaPlayer");
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
