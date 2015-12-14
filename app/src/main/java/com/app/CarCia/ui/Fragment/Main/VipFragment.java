package com.app.CarCia.ui.Fragment.Main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.CarCia.R;
import com.app.CarCia.base.BaseFgm;
import com.app.CarCia.databinding.VipLayout;
import com.app.CarCia.tools.AppTools;
import com.app.CarCia.tools.LogTools;
import com.app.CarCia.tools.SystemTools;
import com.app.CarCia.ui.Activity.BookingActivity;


public class VipFragment extends BaseFgm {
    private VipLayout vipLayout;
    private WebView webView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vipLayout.include.toolBar.inflateMenu(R.menu.menu_paste_pic);
        vipLayout.include.title.setText(R.string.vip);
    }

    @Override
    protected void initViews() {
        vipLayout = (VipLayout) viewDataBinding;
        webView = vipLayout.webView;
    }

    @Override
    protected void initEvents() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.loadUrl("http://st.zhundu.cc/users/garcianetcn/api/garcia_app/Login/index");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("tel:")) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(url));
                    startActivity(intent);
                } else
                    view.loadUrl(url);
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.addJavascriptInterface(new DemoJavaScriptInterface(), "vipOrder");
    }

    class DemoJavaScriptInterface {

        @JavascriptInterface
        public void order(final String id) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (id != null) {
                        Intent intent = new Intent().putExtra("auth", id).setClass(getActivity(),
                                BookingActivity.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_vip;
    }

    @Override
    public boolean isHasOptionsMenu() {
        return false;
    }

    @Override
    public int menuRes() {
        return R.menu.menu_paste_pic;
    }
}
