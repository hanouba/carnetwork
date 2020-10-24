package com.carnetwork.hansen.util;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.carnetwork.hansen.R;
import com.carnetwork.hansen.ui.main.activity.MainActivity;


/**
 * 创建者 by ${HanSir} on 2018/8/28.
 * 版权所有  WELLTRANS.
 * 说明      消息通知栏
 *
 */

public class NotificationUtils extends ContextWrapper {
    private static final String TAG = "NotificationUtils";
    private NotificationManager manager;
    public static final String channelId = "channel_1";
    public static final String channelName = "通知消息";
    private PendingIntent mPintent;

    public NotificationUtils(Context base) {
        super(base);
    }

    public  void initNotification(){
        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = manager.getNotificationChannel(channelId);
            if (channel.getImportance() == NotificationManager.IMPORTANCE_NONE) {
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(Settings.EXTRA_APP_PACKAGE, getPackageName());
                intent.putExtra(Settings.EXTRA_CHANNEL_ID, channel.getId());
                startActivity(intent);
                Toast.makeText(this, "请手动将通知打开", Toast.LENGTH_SHORT).show();
            }
        }


        //点击跳转的intent
    }
    public void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            createNotificationChannel(channelId, channelName, importance);
        } else {

        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel(String channelId, String channelName, int importance) {
        NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
        NotificationManager notificationManager = (NotificationManager) getSystemService(
                NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(channel);
    }
    private NotificationManager getManager(){
        if (manager == null){
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }
    public Notification.Builder getChannelNotification(String title, String content){
            return new Notification.Builder(getApplicationContext(), channelId)
                    .setContentTitle(title)
                    .setContentText(content)
                    .setContentIntent(mPintent)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);
    }
    public NotificationCompat.Builder getNotification_25(String title, String content){
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setContentIntent(mPintent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setAutoCancel(true);
    }
    public void sendNotification(String title, String content, int notifiId, int code){
        LogUtils.i(TAG,"消息数量"+notifiId+"code"+code+"resid");
        Intent intent1=null;
            intent1 = new Intent(this, MainActivity.class);
      mPintent = PendingIntent.getActivity(this, notifiId, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        if (Build.VERSION.SDK_INT>=26){
            createNotificationChannel();
            Notification notification = getChannelNotification
                    (title, content).build();
            getManager().notify(notifiId,notification);
        }else{
            Notification notification = getNotification_25(title, content).build();
            getManager().notify(notifiId,notification);
        }
    }

    public void clearAllNotify() {
        getManager().cancelAll();
    }


}
