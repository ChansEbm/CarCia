package com.app.CarCia.ui.Fragment.Main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.app.CarCia.AppKeyMap;
import com.app.CarCia.R;
import com.app.CarCia.base.BaseFgm;
import com.app.CarCia.broadcast.UpdateUIBroadcast;
import com.app.CarCia.databinding.FragmentBrandBinding;
import com.app.CarCia.impl.UpdateUIListener;
import com.app.CarCia.tools.AppTools;
import com.app.CarCia.ui.Fragment.Brand.BrandChildFragment;
import com.app.CarCia.ui.Fragment.Brand.BusinessFragment;
import com.app.CarCia.ui.Fragment.Brand.ProductFragment;
import com.app.CarCia.ui.Fragment.Brand.SpecialFragment;
import com.app.CarCia.widget.ClashViewPager;

import java.util.ArrayList;
import java.util.List;


public class BrandFragment extends BaseFgm implements UpdateUIListener {
    private TabLayout tabLayout;
    private ClashViewPager viewPager;
    private FragmentBrandBinding brandBinding;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] tabs;
    private FragmentPagerAdapter adapter;
    private UpdateUIBroadcast updateUIBroadcast;
    private boolean isFromBroadcast = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        updateUIBroadcast = new UpdateUIBroadcast();
        updateUIBroadcast.setListener(this);
        AppTools.registerBroadcast(updateUIBroadcast, AppKeyMap.ACTION_JUMP);
    }

    @Override
    protected void initViews() {
        brandBinding = (FragmentBrandBinding) viewDataBinding;
        tabLayout = brandBinding.tabLayout;
        viewPager = brandBinding.viewPager;
        ProductFragment productFragment = new ProductFragment();
        if (getArguments() != null && !getArguments().isEmpty()) {
            isFromBroadcast = true;
            productFragment.setArguments(getArguments());
        }
        tabs = getResources().getStringArray(R.array.tabs);
        fragments.add(productFragment);
        fragments.add(new BrandChildFragment());
        fragments.add(new SpecialFragment());
        fragments.add(new BusinessFragment());

        adapter = new FragmentAdapter(getChildFragmentManager());
    }

    /**
     *
     */
    @Override
    protected void initEvents() {
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setIsEnable(false);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setTabsFromPagerAdapter(adapter);
        if (isFromBroadcast)
            viewPager.setCurrentItem(0, false);
        else
            viewPager.setCurrentItem(1, false);
    }

    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_brand;
    }

    @Override
    public boolean isHasOptionsMenu() {
        return false;
    }

    @Override
    public int menuRes() {
        return 0;
    }

    @Override
    public void uiUpData(Intent intent) {
        viewPager.setCurrentItem(0, true);
    }

    class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabs[position];
        }
    }
}
