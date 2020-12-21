package com.carnetwork.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.blankj.utilcode.util.LogUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.util.Music;
import com.carnetwork.hansen.util.MusicList;

import java.io.IOException;
import java.util.ArrayList;

public class MusicService extends Service {
    private int mPosition;
    //静态存储上次音乐条目的位置
    static int savePosition;
    private MediaPlayer mMediaPlayer;
    //静态存储上次音乐条目的MediaPlayer
    static MediaPlayer mPreMediaPlayer;
    private ArrayList<Music> mMusicList;
    //静态存储当前音乐是否在播放
    static boolean isPlaying;

    public MusicService() {
    }

    //onCreate()只被执行一次，因此用来做初始化
    @Override
    public void onCreate() {
        super.onCreate();
        mMusicList = MusicList.getMusicData(this);
        LogUtils.d("无声音乐地址onCreate"+mMusicList.get(0).getUrl());
    }

    public class MusicBinder extends Binder {
        //创建一个绑定服务时，必须提供一个客户端与Service交互的IBinder，有三种方法
        //我使用的方法：返回当前Service实例，它具有一些客户端可以公开调用的公开方法
        public MusicService getService() {
            return MusicService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Notification notification = new Notification(R.drawable.ic_launcher, "title",System.currentTimeMillis());
        PendingIntent pintent = PendingIntent.getService(this, 0, intent, 0);
        startForeground(0, notification);//设置最高级进程。 id 为 0状态栏的 notification 将不会显示
        return new MusicBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.d("无声音乐地址oonStartCommand");
//        mPosition = intent.getExtras().getInt("position", -1);
        mPosition = 3;
        if (mPreMediaPlayer == null || mPosition != savePosition) {
            playMusic(mPosition);
        } else {
            mMediaPlayer = mPreMediaPlayer;
            mPosition = savePosition;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 播放音乐
     */
    public void playMusic(int position) {
        mMediaPlayer = new MediaPlayer();
        if (mPreMediaPlayer != null) {
            mPreMediaPlayer.stop();
            mPreMediaPlayer.release();
        }
        mPreMediaPlayer = mMediaPlayer;
        savePosition = position;
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource("http://music.163.com/song/media/outer/url?id=447925558.mp3");
//            LogUtils.d("无声音乐地址"+mMusicList.get(position).getUrl());
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                playMusic(mPosition);
            }
        });
    }

    /**
     * 按钮点击：播放音乐
     */
    public void play() {
        if (mMediaPlayer.isPlaying()) {
            mMediaPlayer.pause();
            isPlaying = false;
        } else {
            mMediaPlayer.start();
            isPlaying = true;
        }
    }

    /**
     * 按钮点击：下一首
     */
    public void next(int offset) {
        mPosition += offset;
        mPosition = (mMusicList.size() + mPosition) % mMusicList.size();
        playMusic(mPosition);
    }

    /**
     * 获取当前音乐的名字
     */
    public String getName() {
        return mMusicList.get(mPosition).getName();
    }

    /**
     * 获取当前音乐的播放时间
     */
    public int getTime() {
        return mMusicList.get(mPosition).getTime();
    }

    /**
     * 获取当前播放位置
     */
    public int getCurrent() {
        return mMediaPlayer.getCurrentPosition();
    }

    /**
     * 设置音乐播放的进度
     */
    public void seekTo(int progress) {
        mMediaPlayer.seekTo(progress);
    }
}
