package com.app.CarCia.ui.Fragment.Brand;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.app.CarCia.BrandChildLayout;
import com.app.CarCia.R;
import com.app.CarCia.base.BaseFgm;

/**
 * A simple {@link Fragment} subclass.
 */
public class BrandChildFragment extends BaseFgm {

    private BrandChildLayout brandChildLayout;
    private WebView webView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void initViews() {
        brandChildLayout = (BrandChildLayout) viewDataBinding;
        webView = brandChildLayout.webView;

    }

    @Override
    protected void initEvents() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode
                (WebSettings.LOAD_NO_CACHE);
        webView.loadUrl("http://diy.appbaba.com/garcia/index.php?m=content&c=app&a=brand");
    }

    @Override
    protected void onClick(int id, View view) {

    }

    public boolean onKeyDown(int keyCode) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            if (webView.canGoBack()) {
                webView.goBack();
                return true;
            }
        return false;
    }
    @Override
    protected int getContentView() {
        return R.layout.fragment_brand_child;
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
