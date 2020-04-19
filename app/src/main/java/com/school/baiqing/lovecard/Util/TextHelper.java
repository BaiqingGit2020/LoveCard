package com.school.baiqing.lovecard.Util;

import android.widget.Toast;

import com.school.baiqing.lovecard.MyApplication;


/**
 * Created by zhao on 2016/10/26.
 */

public class TextHelper {

    public final static String SharePreference = "LoveCard";
    public final static String User_woman = "小可爱";
    public final static String IsLanuch = "IsLanuch";
    public final static String User = "User";

    public static void showText(final String text){

        MyApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.getApplication(),text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showLongText(final String text){

        MyApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MyApplication.getApplication(),text, Toast.LENGTH_LONG).show();
            }
        });
    }
}