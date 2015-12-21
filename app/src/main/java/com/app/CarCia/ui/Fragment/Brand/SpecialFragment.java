package com.app.CarCia.ui.Fragment.Brand;


import android.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.CarCia.R;
import com.app.CarCia.SpecialLayout;
import com.app.CarCia.base.BaseFgm;

import java.lang.annotation.Annotation;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpecialFragment extends BaseFgm {
    private SpecialLayout specialLayout;
    private WebView webView;

    @Override
    protected void initViews() {
        specialLayout = (SpecialLayout) viewDataBinding;
        webView = specialLayout.webView;
    }

    @Override
    protected void initEvents() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("http://diy.appbaba.com/garcia/index" +
                ".php?m=content&c=app&a=subject_list");
        webView.addJavascriptInterface(new WebJsonShare(), "share");

    }

    class WebJsonShare {

        @JavascriptInterface
        void onShare(String json) {

        }

    }


    /**
     * @param keyCode
     * @return
     */
    public boolean onKeyDown(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        return false;
    }


    @Override
    protected void onClick(int id, View view) {

    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_special;
    }

    @Override
    public boolean isHasOptionsMenu() {
        return false;
    }

    @Override
    public int menuRes() {
        return 0;
    }


}
