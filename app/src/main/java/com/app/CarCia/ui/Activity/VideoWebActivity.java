package com.app.CarCia.ui.Activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.app.CarCia.R;
import com.app.CarCia.VideoWebLayout;
import com.app.CarCia.base.BaseAty;

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
        webView = videoWebLayout.webView;
    }

    @Override
    protected void initEvents() {
        webView.setWebChromeClient(new WebChromeClient());

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setAllowFileAccess(true);
        webView.getSettings().setDefaultTextEncodingName("UTF-8");
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setVisibility(View.VISIBLE);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);

        String video = "\"<html><body>Youtube video .. <br> <iframe width=\"100%\" height=\"100%\" src=\"http://www.iermu.com/video/30e4242a5e47874fd7e7cf88dcd3be5b/3632437978?l=\" frameborder=\"0\" allowfullscreen></iframe></body></html>\";";
        webView.loadData(video, "text/html", "utf-8");

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_video_web;
    }

    @Override
    protected void onClick(int id, View view) {

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
