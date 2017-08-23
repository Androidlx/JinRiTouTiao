package com.bwie.lianxi;

import android.app.Application;
import android.content.Context;
import android.util.Config;
import android.util.Log;

import com.mob.MobSDK;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;


import org.xutils.x;

import java.io.File;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 北城 on 2017/8/4.
 */

public class AppApplication extends Application {
    public static AppApplication mAppApplication;
    {
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
    }
    @Override
    public void onCreate() {
        super.onCreate();
        initImageLoader(getApplicationContext());
        mAppApplication = this;
        x.Ext.init(this);
        UMShareAPI.get(this);
        MobSDK.init(this, "1ff59cdc24a76", "109588a846e5ce1f9cb601ac5a3ac049");
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }
    public static Context getAppContext(){
        return mAppApplication;
    }

    public static AppApplication getApp() {
        return mAppApplication;
    }

    public static void initImageLoader(Context context) {
        String s = context.getCacheDir().getPath()+"/SWJ";
        //创建配置ImageLoader(所有的选项都是可选的,只使用那些你真的想定制)，这个可以设定在APPLACATION里面，设置为全局的配置参数
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(context)
                .threadPoolSize(3)//线程池内加载的数量
                .denyCacheImageMultipleSizesInMemory()//.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation你可以通过自己的内存缓存实现
                .memoryCacheSize(2 * 1024 * 1024)
                .diskCacheSize(50 * 1024 * 1024)
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())//将保存的时候的URI名称用MD5 加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .diskCache(new UnlimitedDiskCache(new File(s)))
                //自定义缓存路径//.imageDownloader(new BaseImageDownloader(context, 5 * 1000, 30 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                .build();
        ImageLoader.getInstance().init(config);//全局初始化此配置
    }
}
