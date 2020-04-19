package com.school.baiqing.lovecard.greendao.Util;


import com.school.baiqing.lovecard.MyApplication;
import com.school.baiqing.lovecard.greendao.gen.DaoMaster;
import com.school.baiqing.lovecard.greendao.gen.DaoSession;

/**
 * Created by zhao on 2017/3/15.
 */

public class GreenDaoManager {
    private static GreenDaoManager instance;
    private static DaoMaster daoMaster;
    private static MySQLiteOpenHelper mySQLiteOpenHelper;

    public static GreenDaoManager getInstance() {
        if (instance == null) {
            instance = new GreenDaoManager();
        }
        return instance;
    }

    public GreenDaoManager(){
        mySQLiteOpenHelper = new MySQLiteOpenHelper(MyApplication.getmContext(), "Card" , null);
        daoMaster = new DaoMaster(mySQLiteOpenHelper.getWritableDatabase());
    }



    public DaoSession getSession(){
       return daoMaster.newSession();
    }

}
