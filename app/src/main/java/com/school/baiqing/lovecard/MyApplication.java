package com.school.baiqing.lovecard;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyApplication extends Application
{

    private static Handler handler = new Handler();
    private static MyApplication application;

    private ExecutorService mFixedThreadPool;
    @Override
    public void onCreate()
    {
        super.onCreate();
        application = this;
        mFixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());//初始化线程池

    }
    /**
     * 主线程执行
     *
     * @param runnable
     */
    public static void runOnUiThread(Runnable runnable) {
        handler.post(runnable);
    }

    public static MyApplication getApplication() {
        return application;
    }

    public static Context getmContext() {
        return application;
    }

    public void newThread(Runnable runnable) {

        try {
            mFixedThreadPool.execute(runnable);
        } catch (Exception e) {
            e.printStackTrace();
            mFixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());//初始化线程池
            mFixedThreadPool.execute(runnable);
        }
    }
    public void shutdownThreadPool(){
        mFixedThreadPool.shutdownNow();
    }

}



