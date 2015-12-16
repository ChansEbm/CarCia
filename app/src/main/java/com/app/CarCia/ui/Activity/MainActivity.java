package com.app.CarCia.ui.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;

import com.app.CarCia.AppKeyMap;
import com.app.CarCia.MainBinding;
import com.app.CarCia.R;
import com.app.CarCia.base.BaseAty;
import com.app.CarCia.broadcast.UpdateUIBroadcast;
import com.app.CarCia.entity.HomeBean;
import com.app.CarCia.impl.UpdateUIListener;
import com.app.CarCia.tools.AppTools;
import com.app.CarCia.ui.Fragment.Main.BrandFragment;
import com.app.CarCia.ui.Fragment.Main.HomeFragment;
import com.app.CarCia.ui.Fragment.Main.MoreFragment;
import com.app.CarCia.ui.Fragment.Main.VipFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseAty implements RadioGroup.OnCheckedChangeListener,
        UpdateUIListener {

    private FragmentTransaction fragmentTransaction;
    private List<Fragment> fragmentList = new ArrayList<>();
    private Fragment fragment;
    private MainBinding mainBinding;
    private UpdateUIBroadcast broadcast;
    private HomeBean.ListEntity listEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        mainBinding = (MainBinding) viewDataBinding;
    }

    @Override
    protected void initViews() {

        broadcast = new UpdateUIBroadcast();

        if (fragmentList.isEmpty()) {
            fragmentList.add(new HomeFragment());
            fragmentList.add(new BrandFragment());
            fragmentList.add(new VipFragment());
            fragmentList.add(new MoreFragment());
            fragment = fragmentList.get(0);
        }

        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fragment_content, fragment).commitAllowingStateLoss();
        }
    }

    @Override
    protected void initEvents() {
        mainBinding.radioGroup.setOnCheckedChangeListener(this);
        broadcast.setListener(this);
        AppTools.registerBroadcast(broadcast, AppKeyMap.ACTION_JUMP, AppKeyMap.ACTION_INTO_BRAND);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus)
//            new ShareDialog(this).show();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onClick(int id, View view) {

    }

    private void changeFragment(int position) {
        Fragment newFragment = fragmentList.get(position);
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (fragment != newFragment) {
            if (!newFragment.isAdded()) {
                if (position == 1) {
                    Bundle bundle = new Bundle();
                    if (listEntity != null)
                        bundle.putSerializable("listEntity", listEntity);
                    newFragment.setArguments(bundle);
                }
                fragmentTransaction.hide(fragment).add(R.id.fragment_content, newFragment)
                        .commitAllowingStateLoss();
            } else {
                fragmentTransaction.hide(fragment).show(newFragment).commitAllowingStateLoss();
            }
            fragment = newFragment;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_home:
                changeFragment(0);
                break;
            case R.id.rb_brand:
                changeFragment(1);
                break;
            case R.id.rb_vip:
                changeFragment(2);
                break;
            case R.id.rb_more:
                changeFragment(3);
                break;
        }
    }

    @Override
    public void uiUpData(Intent intent) {
        listEntity = (HomeBean.ListEntity) intent.getSerializableExtra(AppKeyMap.ACTION_KEY);
        mainBinding.radioGroup.check(R.id.rb_brand);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            android.os.Process.killProcess(Process.myPid());
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
