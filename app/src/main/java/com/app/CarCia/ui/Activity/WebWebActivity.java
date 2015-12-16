package com.app.CarCia.ui.Activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.CarCia.GuideLayout;
import com.app.CarCia.R;
import com.app.CarCia.base.BaseAty;

public class WebWebActivity extends BaseAty {
    private GuideLayout guideLayout;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        guideLayout = (GuideLayout) viewDataBinding;
    }

    @Override
    protected void initViews() {
        webView = guideLayout.webView;
    }

    @Override
    protected void initEvents() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("http://m.garcia.net.cn/");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_user_guide;
    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
