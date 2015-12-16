package com.app.CarCia.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.app.CarCia.R;
import com.app.CarCia.adapters.LaunchAdapter;
import com.app.CarCia.base.BaseAty;
import com.app.CarCia.databinding.LaunchLayout;
import com.app.CarCia.tools.AppTools;

public class LaunchActivity extends BaseAty implements ViewPager.OnPageChangeListener {
    private LaunchLayout launchLayout;
    private ViewPager viewPager;
    private boolean isScrolled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        launchLayout = (LaunchLayout) viewDataBinding;
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
                if (viewPager.getCurrentItem() == viewPager.getAdapter().getCount() - 1 && !isScrolled) {
                    Intent intent=new Intent();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.setClass(this,MainActivity.class);
                    startActivity(intent);
                    AppTools.removeSingleActivity(this);
                }
                isScrolled = true;
                break;
        }
    }
}
