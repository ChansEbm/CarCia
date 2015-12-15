package com.app.CarCia.ui.Activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.CarCia.R;
import com.app.CarCia.VideoWebLayout;
import com.app.CarCia.base.BaseAty;

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

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return true;
            }
        });

        webView.loadUrl("http://www.iermu.com/view_share.php?shareid=b4f433b3908bc22cc05decfa56ed5b9f&uk=1462218351&share=2");

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_video_web;
    }

    @Override
    protected void onClick(int id, View view) {

    }
}
