package com.school.baiqing.lovecard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.school.baiqing.lovecard.AddDialog.CardDialogFragment;
import com.school.baiqing.lovecard.AddDialog.DialogListener;
import com.school.baiqing.lovecard.AddDialog.EditDialogFragment;
import com.school.baiqing.lovecard.SwipeCardView.ItemListener;
import com.school.baiqing.lovecard.SwipeCardView.LoveCardAdapter;
import com.school.baiqing.lovecard.SwipeCardView.SwipeCardLayoutManager;
import com.school.baiqing.lovecard.SwipeCardView.SwipeCardRecyclerView;
import com.school.baiqing.lovecard.Util.TextHelper;
import com.school.baiqing.lovecard.Util.TimeHelper;
import com.school.baiqing.lovecard.greendao.Util.CardService;
import com.school.baiqing.lovecard.greendao.entity.Card;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Toolbar toolbar;
    FloatingActionButton fab;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    SwipeCardRecyclerView swipeCardRecyclerView;
    SpinKitView spinKitView;
    WebView webView;
    ImageView headImage;
    TextView headtext;

    List<Card> cards = new ArrayList<>();
    List<Card> cardsHolder = new ArrayList<>();
    LoveCardAdapter loveCardAdapter;
    CardService cardService = new CardService();
    private long mExitTime = 0;

    SharedPreferences sharedPreferences ; //私有数据


    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    initAdapter();
                    break;
                case 2:
                    spinKitView.setVisibility(View.VISIBLE);
                    swipeCardRecyclerView.setVisibility(View.INVISIBLE);
                    initData();
                    initAdapter();
                    delay(2000);
                    break;
                case 3:
                    spinKitView.setVisibility(View.INVISIBLE);
                    swipeCardRecyclerView.setVisibility(View.VISIBLE);
                    break;
            }
            return false;
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setwindow();
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(TextHelper.SharePreference, Context.MODE_PRIVATE);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cardsHolder = cardService.getAllCards();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddDialog();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View view = navigationView.inflateHeaderView(R.layout.nav_header_main);
        headImage = view.findViewById(R.id.head_imageView);
        headtext = view.findViewById(R.id.tv_user_name);
        boolean islanuch = sharedPreferences.getBoolean(TextHelper.IsLanuch,false);
        String user = sharedPreferences.getString(TextHelper.User,"小可爱");
        if(islanuch && user!=null && user.equals("小可爱")){
            headImage.setImageResource(R.mipmap.ic_user_women);
            headtext.setText("小可爱");
        }else if(islanuch && user!=null && user.equals("大可爱")){
            headImage.setImageResource(R.mipmap.ic_user_men);
            headtext.setText("大可爱");
        }

        swipeCardRecyclerView = findViewById(R.id.SwipCardView);
        swipeCardRecyclerView.setLayoutManager(new SwipeCardLayoutManager());
        spinKitView = findViewById(R.id.spin_kit_love_card);

        spinKitView.setVisibility(View.INVISIBLE);

        initData();
        initAdapter();

        webView = (WebView) findViewById(R.id.wv_baiqing);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下，我想让webView加载http://ask.csdn.net/questions/178242
                if (!request.getUrl().toString().contains("baiqing")){
                    view.loadUrl("https://www.baiqing.work");
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(webView.getVisibility() == View.VISIBLE){
                webView.setVisibility(View.INVISIBLE);
        }
        if (id == R.id.nav_camera) {
            // Handle the camera action
            cardsHolder = cardService.getAllCards();
            handler.sendMessage(handler.obtainMessage(2));
        } else if (id == R.id.nav_gallery) {
            cardsHolder = cardService.findCardByFrom("Duck爱");
            handler.sendMessage(handler.obtainMessage(2));
        } else if (id == R.id.nav_slideshow) {
            cardsHolder = cardService.findCardByTo("Duck爱");
            handler.sendMessage(handler.obtainMessage(2));
        } else if (id == R.id.nav_manage) {
            cardsHolder = cardService.findCardByFinish(false);
            handler.sendMessage(handler.obtainMessage(2));
        } else if (id == R.id.nav_share) {
            handler.sendMessage(handler.obtainMessage(2));
        } else if (id == R.id.nav_send) {
            webView.loadUrl("https://www.baiqing.work/love");
            webView.setVisibility(View.VISIBLE);
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void initData(){
        cards.clear();
        cards.addAll(cardsHolder);
    }

    public void initAdapter(){
        loveCardAdapter = new LoveCardAdapter(this,cards);
        swipeCardRecyclerView.setAdapter(loveCardAdapter);
        swipeCardRecyclerView.setItemListener(new ItemListener() {
            @Override
            public void onRightRemoved() {
                if(cards.size()==1){
                    cardsHolder = cardService.getAllCards();
                    handler.sendMessage(handler.obtainMessage(2));
                }
            }

            @Override
            public void onLeftRemoved() {
                if(cards.size()==1){
                    cardsHolder = cardService.getAllCards();
                    handler.sendMessage(handler.obtainMessage(2));
                }
            }

            @Override
            public void onItemClick() {
                showEditDialog(cards.get(cards.size()-1));
            }
        });
    }

    public void showEditDialog(Card card){
        EditDialogFragment fragment = EditDialogFragment.newInstance(card);
        fragment.setDialogListener(new DialogListener() {
            @Override
            public void onFinish(Card card) {
                cardService.updateCard(card);
                cardsHolder = cardService.getAllCards();
                cards.set(cards.size()-1,card);
                loveCardAdapter.notifyItemChanged(cards.size()-1);
//                handler.sendMessage(handler.obtainMessage(2));
                TextHelper.showLongText("卡卡修改成功");
            }

            @Override
            public void onCancle(Card card) {
                cardService.deleteCard(card);
                cardsHolder = cardService.getAllCards();
                handler.sendMessage(handler.obtainMessage(2));
                TextHelper.showLongText("卡卡删除成功");

            }
        });
        fragment.show(getSupportFragmentManager(),"");
    }

    public void showAddDialog(){
        CardDialogFragment fragment = CardDialogFragment.newInstance();
        fragment.setDialogListener(new DialogListener() {
            @Override
            public void onFinish(Card card) {
                cardService.addCard(card);
                TextHelper.showLongText("卡卡添加成功");
                cardsHolder = cardService.getAllCards();
                handler.sendMessage(handler.obtainMessage(2));
            }

            @Override
            public void onCancle(Card card) {

            }
        });
        fragment.show(getSupportFragmentManager(),"");
    }

    public void delay(final int milin){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(milin);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                handler.sendMessage(handler.obtainMessage(3));
            }
        }).start();
    }

    public void setwindow(){
        Window window = getWindow();
        //After LOLLIPOP not translucent status bar
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Then call setStatusBarColor.
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.origin));
    }

    @Override
    public void onBackPressed() {
        if(webView.getVisibility() == View.VISIBLE){
            if( webView.canGoBack()){
                webView.goBack();
            }else{
                webView.setVisibility(View.INVISIBLE);
            }
            return;
        }
        if (System.currentTimeMillis() - mExitTime < 2000) {
            super.onBackPressed();
        } else {
            mExitTime = System.currentTimeMillis();
            TextHelper.showText("再按一次退出");
            return;
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
    }
}
