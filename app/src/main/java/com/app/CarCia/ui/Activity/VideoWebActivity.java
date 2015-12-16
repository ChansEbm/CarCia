package com.app.CarCia.ui.Activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.CarCia.R;
import com.app.CarCia.VideoWebLayout;
import com.app.CarCia.base.BaseAty;
import com.app.CarCia.tools.AppTools;

import java.lang.reflect.InvocationTargetException;

public class VideoWebActivity extends BaseAty {
    private WebView webView;
    private VideoWebLayout videoWebLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        videoWebLayout = (VideoWebLayout) viewDataBinding;
    }

    @Override
    protected void initViews() {
        defaultTitleBar(this).setTitle("实时工厂");
        webView = videoWebLayout.webView;
    }

    @Override
    protected void initEvents() {
        webView.setWebChromeClient(new WebChromeClient());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }
        });
        webView.loadUrl("http://st.zhundu.cc/users/garcianetcn/api/garcia_app/Login/factory");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_video_web;
    }

    @Override
    protected void onClick(int id, View view) {
        if (id == R.id.nav) {
            canGoBack();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            canGoBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void canGoBack() {
        if (webView.canGoBack())
            webView.goBack();
        else
            AppTools.removeSingleActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            Class.forName("android.webkit.WebView")
                    .getMethod("onPause", (Class[]) null)
                    .invoke(webView, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
