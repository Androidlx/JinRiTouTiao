package com.bwie.lianxi.shujuku;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andy.library.ChannelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 北城 on 2017/8/16.
 */

public class DBUtils {

    private final SQLiteDatabase db;

    public DBUtils(Context context) {
        SQLite sqLite = new SQLite(context);
        db = sqLite.getWritableDatabase();
    }

    public void add(List<ChannelBean> channelBeanList) {
        if (channelBeanList == null || channelBeanList.size() < 0) {
            return;
        }
        for (ChannelBean channelBean : channelBeanList) {
            ContentValues values = new ContentValues();
            values.put("name", channelBean.getName());
            values.put("selected", channelBean.isSelect());
            db.insert("channels", null, values);
        }
    }

    public List<ChannelBean> chaxun(){
        Cursor cursor = db.query("channels", null, null, null, null, null, null);
        List<ChannelBean> channelBeanList = new ArrayList<>();
        ChannelBean channelBean;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int selected = cursor.getInt(cursor.getColumnIndex("selected"));
            channelBeanList.add(new ChannelBean(name, selected == 0 ? false : true));
        }
        cursor.close();
        return channelBeanList;
    }
    public List<ChannelBean> tiaojian(){
        Cursor cursor = db.query("channels", null, "selected=?", new String[]{"1"}, null, null, null);
        List<ChannelBean> channelBeanList = new ArrayList<>();
        ChannelBean channelBean;
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            int selected = cursor.getInt(cursor.getColumnIndex("selected"));
            channelBeanList.add(new ChannelBean(name, selected == 0 ? false : true));
        }
        cursor.close();
        return channelBeanList;
    }

    public void clearChannels() {
        db.delete("channels", null, null);
    }
}
