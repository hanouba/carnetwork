package com.carnetwork.hansen.mvp.model.db;

import android.database.sqlite.SQLiteDatabase;

import com.carnetwork.hansen.app.Constants;
import com.carnetwork.hansen.app.MyApplication;


import java.util.List;

import javax.inject.Inject;

/**
 * 创建者 by ${HanSir} on 2018/12/13.
 * 版权所有  WELLTRANS.
 * 说明
 */

public class GreenHelper implements DBHelper {
    private LoginInfoDao loginInfoDao;

    private UserLocationInfoDao userLocationInfoDao;


    private static DaoSession mDaoSession;


    public static GreenHelper getInstance() {
        return GreenHelper.SingletonHolder.instance;
    }

    private static class SingletonHolder {
        private static GreenHelper instance = new GreenHelper();
    }

    @Inject
    public GreenHelper() {
        //初始化数据库
        setupDatabase();
        loginInfoDao = mDaoSession.getLoginInfoDao();

        userLocationInfoDao = mDaoSession.getUserLocationInfoDao();

    }

    private void setupDatabase() {
        //创建数据库,DevOpenHelper：创建SQLite数据库的SQLiteOpenHelper的具体实现
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(MyApplication.getInstance().getApplicationContext(), Constants.DATABASE_USER_NAME, null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象,DaoMaster：GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者,DaoSession：管理所有的Dao对象，Dao对象中存在着增删改查等API
        mDaoSession = daoMaster.newSession();
    }

    @Override
    public void insertLoginUserInfo(LoginInfo mLoginUserInfo) {
        loginInfoDao.insertOrReplace(mLoginUserInfo);
    }

    @Override
    public List<LoginInfo> loadLoginUserInfo() {
        return loginInfoDao.loadAll();
    }




}
