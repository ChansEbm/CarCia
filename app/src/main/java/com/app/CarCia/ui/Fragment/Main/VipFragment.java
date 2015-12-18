package com.app.CarCia.ui.Fragment.Main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.app.CarCia.AppKeyMap;
import com.app.CarCia.R;
import com.app.CarCia.base.BaseFgm;
import com.app.CarCia.databinding.VipLayout;
import com.app.CarCia.tools.AppTools;
import com.app.CarCia.tools.LogTools;
import com.app.CarCia.ui.Activity.BookingActivity;

public class VipFragment extends BaseFgm implements Toolbar.OnMenuItemClickListener {
    private VipLayout vipLayout;
    private WebView webView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vipLayout.include.toolBar.inflateMenu(R.menu.menu_paste_pic);
        vipLayout.include.toolBar.setOnMenuItemClickListener(this);
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
        webView.addJavascriptInterface(new LoginJs(), "login");
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.paste_pic) {
            if (!TextUtils.isEmpty(AppKeyMap.UserId)) {
                startBookingActivity(AppKeyMap.UserId);
            } else {
                AppTools.showSnackBar(getActivity().findViewById(R.id.fragment_content),
                        "请登录完毕后执行该操作!");
            }
        }
        return true;
    }

    class DemoJavaScriptInterface {

        @JavascriptInterface
        public void order(final String id) {
            startBookingActivity(id);
        }
    }

    private void startBookingActivity(final String userId) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (userId != null) {
                    Intent intent = new Intent().putExtra("auth", userId).setClass(getActivity(),
                            BookingActivity.class);
                    startActivity(intent);
                }
            }
        });
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

    class LoginJs {

        @JavascriptInterface
        public void userId(String userId) {
            AppKeyMap.UserId = userId;
        }
    }

}
