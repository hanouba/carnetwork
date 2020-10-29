package com.carnetwork.hansen.app;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.blankj.utilcode.util.LogUtils;
import com.carnetwork.hansen.mvp.model.db.DaoMaster;
import com.carnetwork.hansen.util.MySQLiteOpenHelper;
import com.carnetwork.hansen.util.SystemUtil;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.smtt.sdk.QbSdk;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


/**
 * 初始化数据的服务，避免占用Application主进程
 */
public class InitializeService extends IntentService {

    private static final String ACTION_INIT = "initApplication";
    ConnectivityManager connectMgr;

    public InitializeService() {
        super("InitializeService");
    }

    /**
     * 开启服务
     *
     * @param context 上下文
     */
    public static void start(Context context) {
        Intent intent = new Intent(context, InitializeService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    /**
     * 处理跳转意图
     *
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_INIT.equals(action)) {
                initApplication();
            }
        }
    }

    /**
     * 初始化服务及第三方应用
     */
    private void initApplication() {

        initBugly();
        upGreenDao();
        //关闭弹窗9.0
        closeAndroidPDialog();
    }

    /**
     * 初始化腾讯X5内核浏览器
     */
    private void initTbs() {
        //初始化X5内核
        QbSdk.setDownloadWithoutWifi(true);//非wifi条件下允许下载X5内核
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.d("onViewInitFinished" + arg0);
            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);

    }


    /**
     * 初始化 腾讯Bugly 日志记录工具
     */
    private void initBugly() {
        Context context = getApplicationContext();
//        获取包名
        String packageName = context.getPackageName();
//        获取进程名
        String processName = SystemUtil.getProcessName(android.os.Process.myPid());
//        创建日志捕获策略
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
//        初始化
//        CrashReport.initCrashReport(context, Constants.BUGLY_ID, false, strategy);

        Bugly.init(getApplicationContext(), Constants.BUGLY_ID, true);
    }

    /**
     * 更新GreenDao数据库
     */
    private void upGreenDao() {
        MySQLiteOpenHelper o = new MySQLiteOpenHelper(this, Constants.GREEN_DB, null);
        DaoMaster daoMaster = new DaoMaster(o.getWritableDatabase());
    }

    /**
     * 关闭android 9.0 系统及以上，会出现的警告弹窗提示
     * <>反射机制</>
     */
    private void closeAndroidPDialog() {
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
