package com.school.baiqing.lovecard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.school.baiqing.lovecard.Util.TextHelper;

public class LanuchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_lanuch);
        Handler handler = new Handler();
        LinearLayout lanuch = findViewById(R.id.love_lanuch);
        LinearLayout welcom = findViewById(R.id.welcom);
        LinearLayout user_woman = findViewById(R.id.user_women);
        LinearLayout user_man = findViewById(R.id.user_man);

        SharedPreferences sharedPreferences = getSharedPreferences(TextHelper.SharePreference, Context.MODE_PRIVATE); //私有数据
        final SharedPreferences.Editor editor = sharedPreferences.edit();//获取编辑器
        boolean isLanuch = sharedPreferences.getBoolean(TextHelper.IsLanuch,false);
        if(!isLanuch){
            lanuch.setVisibility(View.VISIBLE);
            welcom.setVisibility(View.INVISIBLE);
            user_man.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putString(TextHelper.User,"大可爱");
                    editor.putBoolean(TextHelper.IsLanuch,true);
                    startActivity(new Intent(LanuchActivity.this, MainActivity.class));
                    LanuchActivity.this.finish();
                    editor.apply();
                }
            });
            user_woman.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editor.putString(TextHelper.User,"小可爱");
                    editor.putBoolean(TextHelper.IsLanuch,true);
                    startActivity(new Intent(LanuchActivity.this, MainActivity.class));
                    LanuchActivity.this.finish();
                    editor.apply();
                }
            });
        }else {
            lanuch.setVisibility(View.INVISIBLE);
            welcom.setVisibility(View.VISIBLE);
            //当计时结束时，跳转至主界面
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LanuchActivity.this, MainActivity.class));
                    LanuchActivity.this.finish();
                }
            }, 2500);   //持续时间为3秒
        }
    }
}
