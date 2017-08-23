package com.bwie.lianxi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by 北城 on 2017/8/10.
 */

public class JiGuang extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Bundle bundle = intent.getExtras();
            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            String json = bundle.getString(JPushInterface.EXTRA_EXTRA);
            Toast.makeText(context,"json == "+json+message,Toast.LENGTH_SHORT).show();
        }
    }
}
