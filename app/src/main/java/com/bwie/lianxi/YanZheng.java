package com.bwie.lianxi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by 北城 on 2017/8/9.
 */

public class YanZheng extends AppCompatActivity implements View.OnClickListener{

    private EditText phonenumber;
    private Button duanxinyanzheng;
    private Button tijiaoyanzhengma;
    private EditText ma;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.yanzheng);
        phonenumber = (EditText) findViewById(R.id.phonenumber);
        duanxinyanzheng = (Button) findViewById(R.id.duanxinyanzheng);
        tijiaoyanzhengma = (Button) findViewById(R.id.tijiaoyanzhengma);
        ma = (EditText) findViewById(R.id.ma);
        duanxinyanzheng.setOnClickListener(this);
        tijiaoyanzhengma.setOnClickListener(this);
        SMSSDK.registerEventHandler(eh);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.duanxinyanzheng:
                SMSSDK.getVerificationCode("86", phonenumber.getText().toString().trim(), new OnSendMessageHandler(){

                    @Override
                    public boolean onSendMessage(String s, String s1) {
                        return false;
                    }
                });

                break;
            case R.id.tijiaoyanzhengma:
                SMSSDK.submitVerificationCode("86", phonenumber.getText().toString().trim(), ma.getText().toString().trim());

                break;
        }
    }
    EventHandler eh=new EventHandler(){

        @Override
        public void afterEvent(int event, int result, Object data) {

            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                          Intent intent = new Intent(YanZheng.this, MainActivity.class);
                          startActivity(intent);

                            Toast.makeText(YanZheng.this, "验证成功", Toast.LENGTH_SHORT).show();
                        }

                    });
                }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    //获取验证码成功
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Toast.makeText(YanZheng.this, "获取验证码成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                    //返回支持发送验证码的国家列表
                }
            }else{
                ((Throwable)data).printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(YanZheng.this, "失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh);
    }
}
