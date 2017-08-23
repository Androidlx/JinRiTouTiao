package com.bwie.lianxi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.math.BigDecimal;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 北城 on 2017/8/22.
 */

public class SheZhi extends AppCompatActivity implements View.OnClickListener{

    private ImageView fanhui;
    private CheckBox cb2;
    private SharedPreferences.Editor edit;
    private TextView tiaoshi;
    private TextView huancun;
    private ImageView clear;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shezhi);
        fanhui = (ImageView) findViewById(R.id.setting_back);
        tiaoshi = (TextView) findViewById(R.id.tiaoshi);
        cb2 = (CheckBox) findViewById(R.id.cb2);
        huancun = (TextView) findViewById(R.id.huancun);
        clear = (ImageView) findViewById(R.id.clear);
        fanhui.setOnClickListener(this);
        tiaoshi.setOnClickListener(this);
        huancun.setOnClickListener(this);
        clear.setOnClickListener(this);
        boolean b = getSharedPreferences("FLAG", MODE_PRIVATE).getBoolean("key", true);
        SharedPreferences sp = getSharedPreferences("FLAG",MODE_PRIVATE);
        edit = sp.edit();
        cb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edit.putBoolean("key",true).commit();
                    JPushInterface.resumePush(SheZhi.this);
                }else {
                    edit.putBoolean("key",false).commit();
                    JPushInterface.stopPush(SheZhi.this);
                }
            }
        });
        if (b){
            cb2.setChecked(true);
        }else {
            cb2.setChecked(false);
        }
    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.setting_back:
               this.finish();
               break;
           case R.id.tiaoshi:
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
              tiaoshi.setText( Demo.getInstance().getBASE_URL());
               break;
           case R.id.huancun:
               try {
                   String s = size();
                   huancun.setText("当前缓存："+s);
               } catch (Exception e) {
                   e.printStackTrace();
               }
               break;
           case R.id.clear:
               qingchu(SheZhi.this);
               String s = null;
               try {
                   s = size();
               } catch (Exception e) {
                   e.printStackTrace();
               }
               huancun.setText("当前缓存："+s);
               break;
       }
    }

    private static void qingchu(Context context) {
        delete(context.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            delete(context.getExternalCacheDir());
        }
    }

    private static boolean delete(File cacheDir) {
        if (cacheDir != null && cacheDir.isDirectory()) {
            String[] children = cacheDir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = delete(new File(cacheDir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        return cacheDir.delete();
    }

    public String size()throws Exception{
        long cachesize = getFolderSize(this.getCacheDir());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            cachesize += getFolderSize(this.getExternalCacheDir());
        }
        return getFormatSize(cachesize);
    }

    private static long getFolderSize(File file)throws Exception {
        if (!file.exists()) {
            return 0;
        }
        long size = 0;
        try {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果下面还有文件
                if (files[i].isDirectory()) {
                    size = size + getFolderSize(files[i]);
                } else {
                    size = size + files[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "0K";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
