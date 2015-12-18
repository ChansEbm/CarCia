package com.app.CarCia.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;

import com.app.CarCia.R;
import com.app.CarCia.adapters.LaunchAdapter;
import com.app.CarCia.base.BaseAty;
import com.app.CarCia.databinding.LaunchLayout;
import com.app.CarCia.tools.AppTools;
import com.app.CarCia.tools.SharedPreferencesTools;

public class LaunchActivity extends BaseAty implements ViewPager.OnPageChangeListener {
    private LaunchLayout launchLayout;
    private ViewPager viewPager;
    private boolean isScrolled = true;
    private boolean isFirst = true;
    private SharedPreferencesTools sharedPreferencesTools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager
                .LayoutParams.FLAG_FULLSCREEN);//全屏
        super.onCreate(savedInstanceState);
        launchLayout = (LaunchLayout) viewDataBinding;

        sharedPreferencesTools = new SharedPreferencesTools(this);
        isFirst = sharedPreferencesTools.getBoolean("isFirstRunApplication", isFirst);
        if (!isFirst) {
            startMainActivity();
            return;
        }
    }

    @Override
    protected void initViews() {
        viewPager = launchLayout.viewPager;

    }

    @Override
    protected void initEvents() {
        viewPager.setAdapter(new LaunchAdapter());
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_launch;
    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                isScrolled = false;
                break;
            case ViewPager.SCROLL_STATE_SETTLING:
                isScrolled = true;
                break;
            case ViewPager.SCROLL_STATE_IDLE:
                if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 &&
                        !isScrolled) {
                    isFirst = false;
                    sharedPreferencesTools.putBoolean("isFirstRunApplication", isFirst);
                    startMainActivity();
                }
                isScrolled = true;
                break;
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}
