package com.app.CarCia.ui.Activity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.app.CarCia.DetailWebLayout;
import com.app.CarCia.R;
import com.app.CarCia.base.BaseAty;
import com.app.CarCia.tools.AppTools;

public class DetailWebActivity extends BaseAty {

    private DetailWebLayout detailWebLayout;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        detailWebLayout = (DetailWebLayout) viewDataBinding;
    }

    @Override
    protected void initViews() {
        defaultTitleBar(this).setTitle(R.string.pic_detail);
        webView = detailWebLayout.webView;
    }

    @Override
    protected void initEvents() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getIntent().getStringExtra("detailUrl"));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_detail_web;
    }

    @Override
    protected void onClick(int id, View view) {
        if (id == R.id.nav) {
            AppTools.removeSingleActivity(this);
        }
    }
}
