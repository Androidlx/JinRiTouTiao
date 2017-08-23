package com.bwie.lianxi;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

/**
 * Created by 北城 on 2017/8/15.
 */

public class XinWen extends AppCompatActivity implements View.OnClickListener{

    private WebView webView;
    private ProgressBar progress;
    private ImageView fanhui;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xinwen);
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView = (WebView) findViewById(R.id.webView);
        progress = (ProgressBar) findViewById(R.id.progress);
        fanhui = (ImageView) findViewById(R.id.fanhui);
        fanhui.setOnClickListener(this);
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progress.setProgress(newProgress);
            }
        });
    }

    @Override
    public void onClick(View v) {
        this.finish();
    }
}
