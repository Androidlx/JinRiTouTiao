package com.bwie.lianxi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.andy.library.ChannelActivity;
import com.andy.library.ChannelBean;
import com.bwie.lianxi.shujuku.DBUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private SlidingMenu slidingMenu;
    private ImageView qq;
    private ImageView riye;
    private ImageView duanxin;
    private SlidingMenu slidingMenu2;
    private ImageView lixian;
    private Broad broad;
    private ArrayList<ChannelBean> list;
    private ImageView tuozhuai;
    private List<ChannelBean> all;
    private List<ChannelBean> tiaojian;
    private List<ChannelBean> chaxun;
    private DBUtils dbUtils;
    private MyAdapter adapter;
    private ImageView shezhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.zhuti(this);
        setContentView(R.layout.activity_main);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        broad = new Broad();
        registerReceiver(broad,filter);
        HashSet<String> set = new HashSet<>();
        set.add("买包");
        set.add("买手机");
        JPushInterface.setAliasAndTags(this, "shenwenjing", set, new TagAliasCallback() {
            @Override
            public void gotResult(int i, String s, Set<String> set) {

            }
        });

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);


        ImageView imageView = (ImageView) findViewById(R.id.touxiang);
        tuozhuai = (ImageView) findViewById(R.id.tuozhuai);
        imageView.setOnClickListener(this);
        initdata();
        tabLayout.setupWithViewPager(viewPager);
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        viewPager.setOffscreenPageLimit(9);
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu.setBehindOffset(100);
        slidingMenu.setOffsetFadeDegree(0.4f);
        slidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.slindingmenu);
        slidingMenu2 = new SlidingMenu(this);
        slidingMenu2.setMode(SlidingMenu.RIGHT);
        slidingMenu2.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        slidingMenu2.setBehindOffset(100);
        slidingMenu2.setOffsetFadeDegree(0.4f);
        slidingMenu2.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);
        slidingMenu2.setMenu(R.layout.slidingmenu2);
        qq = (ImageView) findViewById(R.id.QQ);
        qq.setOnClickListener(this);
        riye = (ImageView) slidingMenu.findViewById(R.id.riye);
        riye.setOnClickListener(this);
        duanxin = (ImageView) slidingMenu.findViewById(R.id.duanxinyanzheng);
        duanxin.setOnClickListener(this);
        lixian = (ImageView) slidingMenu.findViewById(R.id.lixian);
        lixian.setOnClickListener(this);
        tuozhuai.setOnClickListener(this);
        shezhi = (ImageView) slidingMenu.findViewById(R.id.shezhi);
        shezhi.setOnClickListener(this);

    }

    public void initdata(){
        all = new ArrayList<>();
        tiaojian = new ArrayList<>();
        dbUtils = new DBUtils(this);
        List<ChannelBean> chaxun = dbUtils.chaxun();
        if (chaxun == null || chaxun.size() < 1) {
            ChannelBean channelBean1 = new ChannelBean("推荐", true);
            ChannelBean channelBean2 = new ChannelBean("国内", true);
            ChannelBean channelBean3 = new ChannelBean("体育", true);
            ChannelBean channelBean4 = new ChannelBean("时尚", true);
            ChannelBean channelBean5 = new ChannelBean("社会", true);
            ChannelBean channelBean6 = new ChannelBean("娱乐", true);
            ChannelBean channelBean7 = new ChannelBean("科技", false);
            ChannelBean channelBean8 = new ChannelBean("财经", false);
            ChannelBean channelBean9 = new ChannelBean("军事", false);
            tiaojian.add(channelBean1);
            tiaojian.add(channelBean2);
            tiaojian.add(channelBean3);
            tiaojian.add(channelBean4);
            tiaojian.add(channelBean5);
            tiaojian.add(channelBean6);
            all.add(channelBean1);
            all.add(channelBean2);
            all.add(channelBean3);
            all.add(channelBean4);
            all.add(channelBean5);
            all.add(channelBean6);
            all.add(channelBean7);
            all.add(channelBean8);
            all.add(channelBean9);

            dbUtils.add(all);
        }else {
            all.addAll(chaxun);
            List<ChannelBean> tj = dbUtils.tiaojian();
            tiaojian.addAll(tj);

        }

        adapter = new MyAdapter(getSupportFragmentManager(),tiaojian);
        viewPager.setAdapter(adapter);


    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.touxiang:
                slidingMenu.toggle();
                break;
            case R.id.QQ:
                UMShareAPI.get(this).getPlatformInfo(MainActivity.this, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.riye:
                Utils.gaibian(this);
                break;
            case R.id.duanxinyanzheng:
                Intent intent = new Intent(MainActivity.this, YanZheng.class);
                startActivity(intent);
                break;
            case R.id.lixian:
                String[] string = {"最佳效果", "较省流量", "极省流量"};
                int mode = AppApplication.getAppContext().getSharedPreferences(Demo.SP_NAME, Context.MODE_PRIVATE).getInt(Demo.PICTURE_LOAD_MODE_KEY, 0);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("非wifi网络流量");
                builder.setSingleChoiceItems(string, mode, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AppApplication.getAppContext().getSharedPreferences(Demo.SP_NAME,Context.MODE_PRIVATE).edit().putInt(Demo.PICTURE_LOAD_MODE_KEY,which).commit();
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("取消", null);
                builder.create().show();
                break;
            case R.id.tuozhuai:
                ChannelActivity.startChannelActivity(this, all);
                break;
            case R.id.shezhi:
                Intent tiaozhuan = new Intent(MainActivity.this,SheZhi.class);
                startActivity(tiaozhuan);
                break;

        }
    }
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //授权开始的回调
        }
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            Toast.makeText(getApplicationContext(), "Authorize succeed", Toast.LENGTH_SHORT).show();
          TextView text = (TextView) findViewById(R.id.name);
            ImageView touxiang= (ImageView) findViewById(R.id.QQ);
            String name = data.get("name");
            String tu = data.get("iconurl");
            text.setText(name);
            ImageLoader.getInstance().displayImage(tu,touxiang);

        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
    public class Broad extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if(ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())){
                boolean wang = true;
                ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = manager.getActiveNetworkInfo();
                if(networkInfo != null && networkInfo.isConnected()){
                    if (ConnectivityManager.TYPE_WIFI == networkInfo.getType()) {
                        Toast.makeText(MainActivity.this, "wifi可用，下载吧", Toast.LENGTH_SHORT).show();
                        wang = false;
                    } else if (ConnectivityManager.TYPE_MOBILE == networkInfo.getType()) {
                        Toast.makeText(MainActivity.this, "现在是移动网络，当心", Toast.LENGTH_SHORT).show();
                        wang = true;
                        //获得现在的网络状态 是移动网络，去改变我们的访问接口
                    } else {
                        Toast.makeText(MainActivity.this, "网络不可用，请检查网络", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MainActivity.this, "网络不可用，请检查网络", Toast.LENGTH_SHORT).show();
                }
                Demo.getInstance().changeNetState(wang);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        if(requestCode == ChannelActivity.REQUEST_CODE && resultCode == ChannelActivity.RESULT_CODE){
            String stringExtra = data.getStringExtra(ChannelActivity.RESULT_JSON_KEY);
            if (TextUtils.isEmpty(stringExtra)) {
                return;
            }
            List<ChannelBean> list = new Gson().fromJson(stringExtra, new TypeToken<List<ChannelBean>>() {
            }.getType());
            if (list == null || list.size() < 1) {
                return;
            }
            all.clear();
            tiaojian.clear();
            all.addAll(list);
            for (ChannelBean channelBean : list) {
                boolean select = channelBean.isSelect();
                if (select) {
                    tiaojian.add(channelBean);
                }
            }
            adapter.notifyDataSetChanged();
            dbUtils.clearChannels();
            dbUtils.add(all);
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            List<Fragment> fragments = supportFragmentManager.getFragments();
            FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
            for (Fragment fragment:fragments) {
                fragmentTransaction.remove(fragment);
            }
            fragmentTransaction.commitAllowingStateLoss();
            recreate();
        }

    }

}
