package com.app.CarCia.ui.Fragment.Main;

import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.CarCia.AppKeyMap;
import com.app.CarCia.BottomBinding;
import com.app.CarCia.MiddleBinding;
import com.app.CarCia.R;
import com.app.CarCia.TopLayout;
import com.app.CarCia.adapters.CommonBinderAdapter;
import com.app.CarCia.adapters.CommonBinderHolder;
import com.app.CarCia.adapters.MultiAdapter;
import com.app.CarCia.base.BaseFgm;
import com.app.CarCia.databinding.FragmentHomeBinding;
import com.app.CarCia.entity.HomeBean;
import com.app.CarCia.impl.OnPagerClickListener;
import com.app.CarCia.model.NetworkModel;
import com.app.CarCia.tools.AppTools;
import com.app.CarCia.tools.WrappableGridLayoutManager;
import com.app.CarCia.ui.Activity.VideoWebActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.InstrumentedActivity;

public class HomeFragment extends BaseFgm implements OnPagerClickListener, Toolbar
        .OnMenuItemClickListener {
    private FragmentHomeBinding fragmentHomeBinding;
    private MultiAdapter<HomeBean.ListEntity> topAdapter;//顶部Adapter
    private CommonBinderAdapter<HomeBean.ListEntity> middleAdapter;//中部Adapter
    private CommonBinderAdapter<HomeBean.ListEntity> bottomAdapter;//底部Adapter
    private List<HomeBean.ListEntity> topList = new ArrayList<>();
    private List<HomeBean.ListEntity> middleList = new ArrayList<>();
    private List<HomeBean.ListEntity> bottomList = new ArrayList<>();

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentHomeBinding.include.toolBar.inflateMenu(R.menu.menu_online);
        fragmentHomeBinding.include.toolBar.setNavigationIcon(R.mipmap.logo_carcia);
        fragmentHomeBinding.include.toolBar.setOnMenuItemClickListener(this);
    }

    @Override
    protected void initViews() {
        fragmentHomeBinding = (FragmentHomeBinding) viewDataBinding;
        initTopRecycler();
        initMiddleRecycler();
        initBottomRecycler();

        new NetworkModel(getActivity()).topAdvertisement(fragmentHomeBinding.topViewPager, this);
        new NetworkModel(getActivity()).bottomAdvertisement(fragmentHomeBinding
                .bottomViewPager, this);
    }

    @Override
    protected void initEvents() {

        topAdapter.setBinderOnItemClickListener(this);
        middleAdapter.setBinderOnItemClickListener(this);
        bottomAdapter.setBinderOnItemClickListener(this);

        fragmentHomeBinding.topRecyclerView.setLayoutManager(new WrappableGridLayoutManager
                (getActivity(), 3));
        fragmentHomeBinding.topRecyclerView.setAdapter(topAdapter);

        fragmentHomeBinding.middleRecyclerView.setAdapter(middleAdapter);
        fragmentHomeBinding.middleRecyclerView.setLayoutManager(new WrappableGridLayoutManager
                (getActivity(), 4));

        fragmentHomeBinding.bottomRecyclerView.setAdapter(bottomAdapter);
        fragmentHomeBinding.bottomRecyclerView.setLayoutManager(new WrappableGridLayoutManager
                (getActivity(), 3));
    }

    @Override
    protected void onClick(int id, View view) {

    }

    private void initTopRecycler() {

        topAdapter = new MultiAdapter<HomeBean.ListEntity>
                (getActivity(), topList, R.layout.item_home_top, R.layout.item_home_top_sec) {

            @Override
            public int getItemViewType(int position) {
                if (topList.get(position).isSecond()) {
                    return SECOND_LAYOUT;
                } else {
                    return FIRST_LAYOUT;
                }
            }

            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, HomeBean.ListEntity listEntity) {
                if (!listEntity.isSecond()) {
                    ((TopLayout) viewDataBinding).simpleDraweeView.setImageURI(Uri.parse(listEntity
                            .getImage()));
                    ((TopLayout) viewDataBinding).setTop(listEntity);
                }
            }
        };
        new NetworkModel(getActivity()).content(true, "Style", topList, topAdapter);

    }

    private void initMiddleRecycler() {


        middleAdapter = new CommonBinderAdapter<HomeBean.ListEntity>(getActivity(), R.layout
                .item_home_middle, middleList) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, HomeBean.ListEntity listEntity) {
                MiddleBinding middle = (MiddleBinding) viewDataBinding;
                middle.simpleDraweeView.setImageURI(Uri.parse(listEntity.getImage()));
                ((MiddleBinding) viewDataBinding).setMiddle(listEntity);
            }
        };
        new NetworkModel(getActivity()).content(false, "Space", middleList, middleAdapter);

    }

    private void initBottomRecycler() {

        bottomAdapter = new CommonBinderAdapter<HomeBean.ListEntity>(getActivity(), R.layout
                .item_home_bottom, bottomList) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, HomeBean.ListEntity listEntity) {
                BottomBinding bottom = (BottomBinding) viewDataBinding;
                bottom.simpleDraweeView.setImageURI(Uri.parse(listEntity.getImage()));
                ((BottomBinding) viewDataBinding).setBottom(listEntity);
            }
        };
        new NetworkModel(getActivity()).content(false, "Category", bottomList, bottomAdapter);

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void onBinderItemClick(View view, int pos) {
        super.onBinderItemClick(view, pos);
        if (view.getId() == R.id.home_top && pos == 0) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.clear();
        HomeBean.ListEntity listEntity;
        switch (view.getId()) {
            case R.id.home_top:
                listEntity = topList.get(pos);
                bundle.putSerializable(AppKeyMap.ACTION_KEY, listEntity);
                break;
            case R.id.home_middle:
                listEntity = middleList.get(pos);
                bundle.putSerializable(AppKeyMap.ACTION_KEY, listEntity);
                break;
            case R.id.home_bottom:
                listEntity = bottomList.get(pos);
                bundle.putSerializable(AppKeyMap.ACTION_KEY, listEntity);
                break;
        }
        AppTools.sendBroadcast(bundle, AppKeyMap.ACTION_JUMP);
    }

    @Override
    public boolean isHasOptionsMenu() {
        return false;
    }

    @Override
    public int menuRes() {
        return R.menu.menu_online;
    }

    @Override
    public void onPagerClick(ViewGroup viewGroup, int pos) {
        if (viewGroup.getId() == R.id.topViewPager) {

        } else if (viewGroup.getId() == R.id.bottomViewPager) {
            AppTools.sendBroadcast(null, AppKeyMap.ACTION_INTO_BRAND);
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.online) {
            start(VideoWebActivity.class);
        }
        return true;
    }
}
