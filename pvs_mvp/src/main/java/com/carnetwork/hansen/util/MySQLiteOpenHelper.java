package com.carnetwork.hansen.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.carnetwork.hansen.mvp.model.db.DaoMaster;
import com.carnetwork.hansen.mvp.model.db.LoginInfoDao;
import com.github.yuweiguocn.library.greendao.MigrationHelper;


import org.greenrobot.greendao.database.Database;

/**
 * 创建者 by ${HanSir} on 2018/12/14.
 * 版权所有  WELLTRANS.

 ---------------------

 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper{
    /**
     *
     * @param context  上下文
     * @param name     原来定义的数据库的名字   新旧数据库一致
     * @param factory  可以null
     */
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    /**
     *
     * @param db
     * @param oldVersion
     * @param newVersion
     *  更新数据库的时候自己调用
     *  需要在升级这里设置一下，必须把这个两个红色字体的参数改成ture
     */
    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        MigrationHelper.migrate(db, new MigrationHelper.ReCreateAllTableListener() {
            @Override
            public void onCreateAllTables(Database db, boolean ifNotExists) {
                DaoMaster.createAllTables(db, true);
            }

            @Override
            public void onDropAllTables(Database db, boolean ifExists) {
                DaoMaster.dropAllTables(db, true);
            }

        }, LoginInfoDao.class);



    }


}
