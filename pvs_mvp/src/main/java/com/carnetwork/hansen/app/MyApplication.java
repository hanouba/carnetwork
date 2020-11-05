package com.carnetwork.hansen.app;



import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.CoordType;
import com.baidu.mapapi.SDKInitializer;
import com.carnetwork.hansen.di.component.AppComponent;
import com.carnetwork.hansen.di.component.DaggerAppComponent;
import com.carnetwork.hansen.di.module.AppModule;
import com.carnetwork.hansen.di.module.HttpModule;
import com.carnetwork.hansen.BuildConfig;

import java.util.HashSet;
import java.util.Set;


/**

 */
public class MyApplication extends Application {
    public static boolean isDebug = BuildConfig.DEBUG;
    private static MyApplication instance;
    public static AppComponent appComponent;
    private String TAG = "MyApplication";

    //    单例模式，获取当前对象
    public static synchronized MyApplication getInstance() {
        return instance;
    }


    //    activity的集合
    private Set<Activity> allActivities;

    /**
     * 类的创建，最早会执行的方法
     */
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        InitializeService.start(this);


        SDKInitializer.initialize(this);
        //自4.3.0起，百度地图SDK所有接口均支持百度坐标和国测局坐标，用此方法设置您使用的坐标类型.
        //包括BD09LL和GCJ02两种坐标，默认是BD09LL坐标。
        SDKInitializer.setCoordType(CoordType.BD09LL);


    }


    /**
     * 关联上下文
     *
     * @param base 上下文
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }




    /**
     * 获取（初始化）数据操作对象
     *
     * @return 数据操作缓存对象
     */
    public static AppComponent getAppComponent() {
        if (appComponent == null) {
            appComponent = DaggerAppComponent.builder()
                    .appModule(new AppModule(instance))
                    .httpModule(new HttpModule())
                    .build();
        }
        return appComponent;
    }

    /**
     * 添加创建的activity
     *
     * @param act activity对象
     */
    public void addActivity(Activity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    /**
     * 移除当前activity对象
     *
     * @param act activity对象
     */
    public void removeActivity(Activity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }

    /**
     * 退出（调用该方法，应用将完全退出，不可被拉起）
     */
    public void exitApp() {


//        如果记录的activity不为空
        if (allActivities != null) {
//            加入异步锁
            synchronized (allActivities) {
                for (Activity act : allActivities) {
//                    循环遍历，关闭activity
                    act.finish();
                }
            }
        }
//        找到自身进程ID，杀死
        android.os.Process.killProcess(android.os.Process.myPid());
//        系统推出
        System.exit(0);
    }

}
