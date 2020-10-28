/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.carnetwork.hansen.component.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import android.widget.RemoteViews;


import com.carnetwork.hansen.R;
import com.carnetwork.hansen.ui.main.activity.MainActivity;


/**
 * Create by KunMinX at 19/7/17
 */
public class PlayerService extends Service {

    public static final String NOTIFY_PREVIOUS = "pure_music.kunminx.previous";
    public static final String NOTIFY_CLOSE = "pure_music.kunminx.close";
    public static final String NOTIFY_PAUSE = "pure_music.kunminx.pause";
    public static final String NOTIFY_PLAY = "pure_music.kunminx.play";
    public static final String NOTIFY_NEXT = "pure_music.kunminx.next";
    private static final String GROUP_ID = "group_001";
    private static final String CHANNEL_ID = "channel_001";
    //声明这个类  实现里面的接口
    private PlayerCallHelper mPlayerCallHelper;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (mPlayerCallHelper == null) {
            mPlayerCallHelper = new PlayerCallHelper(new PlayerCallHelper.PlayerCallHelperListener() {
                @Override
                public void playAudio() {
                    PlayerManager.getInstance().playAudio();
                }

                @Override
                public boolean isPlaying() {
                    return PlayerManager.getInstance().isPlaying();
                }

                @Override
                public boolean isPaused() {
                    return PlayerManager.getInstance().isPaused();
                }

                @Override
                public void pauseAudio() {
                    PlayerManager.getInstance().pauseAudio();
                }
            });
        }




        mPlayerCallHelper.bindCallListener(getApplicationContext());

        createNotification();
        return START_NOT_STICKY;
    }

    private void createNotification() {
        try {
            String title = "车联APP 正在定位中";

            String summary = "summary";

            RemoteViews simpleContentView = new RemoteViews(
                    getApplicationContext().getPackageName(), R.layout.notify_player_small);



            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setAction("showPlayer");
            PendingIntent contentIntent = PendingIntent.getActivity(
                    this, 0, intent, 0);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationManager notificationManager = (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);

                NotificationChannelGroup playGroup = new NotificationChannelGroup(GROUP_ID, getString(R.string.app_name));
                notificationManager.createNotificationChannelGroup(playGroup);

                NotificationChannel playChannel = new NotificationChannel(CHANNEL_ID,
                        getString(R.string.about_software), NotificationManager.IMPORTANCE_DEFAULT);
                playChannel.setGroup(GROUP_ID);
                notificationManager.createNotificationChannel(playChannel);
            }

            Notification notification = new NotificationCompat.Builder(
                    getApplicationContext(), CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentIntent(contentIntent)
                    .setOnlyAlertOnce(true)
                    .setContentTitle(title).build();

            notification.contentView = simpleContentView;




            notification.contentView.setTextViewText(R.id.player_song_name, title);
            notification.contentView.setTextViewText(R.id.player_author_name, summary);
            notification.bigContentView.setTextViewText(R.id.player_song_name, title);
            notification.bigContentView.setTextViewText(R.id.player_author_name, summary);
            notification.flags |= Notification.FLAG_ONGOING_EVENT;





            startForeground(5, notification);

            mPlayerCallHelper.bindRemoteController(getApplicationContext());
            mPlayerCallHelper.requestAudioFocus(title, summary);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        mPlayerCallHelper.unbindCallListener(getApplicationContext());
        mPlayerCallHelper.unbindRemoteController();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
