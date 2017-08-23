package com.bwie.lianxi.shujuku;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 北城 on 2017/8/16.
 */

public class SQLite extends SQLiteOpenHelper {
    public SQLite(Context context) {
        super(context, "news.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table channels (name varchar(20) ,selected integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
