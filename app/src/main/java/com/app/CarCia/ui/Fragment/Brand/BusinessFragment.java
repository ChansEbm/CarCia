package com.app.CarCia.ui.Fragment.Brand;


import android.app.Fragment;
import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.CarCia.BusinessLayout;
import com.app.CarCia.ItemPicWordLayout;
import com.app.CarCia.R;
import com.app.CarCia.adapters.CommonBinderAdapter;
import com.app.CarCia.adapters.CommonBinderHolder;
import com.app.CarCia.base.BaseFgm;
import com.app.CarCia.entity.ItemProductBean;
import com.app.CarCia.model.NetworkModel;
import com.app.CarCia.ui.Activity.DetailActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class BusinessFragment extends BaseFgm<ItemProductBean.ListEntity> {
    private BusinessLayout businessLayout;
    private RecyclerView recyclerView;

    @Override
    protected void initViews() {
        businessLayout = (BusinessLayout) viewDataBinding;
        recyclerView = businessLayout.recyclerView;

        commonBinderAdapter = new CommonBinderAdapter<ItemProductBean.ListEntity>(getActivity(),
                R.layout.item_pic_word, list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, ItemProductBean.ListEntity listEntity) {
                ItemPicWordLayout itemProductLayout = (ItemPicWordLayout) viewDataBinding;
                itemProductLayout.simpleDraweeView.setImageURI(Uri.parse(listEntity.getThumb()));
                itemProductLayout.setItem(listEntity);
            }
        };
    }

    @Override
    protected void initEvents() {
        recyclerView.setAdapter(commonBinderAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        commonBinderAdapter.setBinderOnItemClickListener(this);
        Map<String, String> map = new HashMap<>();
        map.put("spaceid", "8");
        new NetworkModel(getActivity()).product(businessLayout.getRoot(), map, commonBinderAdapter);
    }


    @Override
    protected void onClick(int id, View view) {

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_business;
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
    public void onBinderItemClick(View view, int pos) {
        ArrayList<ItemProductBean.ListEntity> listEntities = (ArrayList<ItemProductBean
                .ListEntity>) list;
        Intent intent = new Intent().putExtra("listEntity", listEntities).putExtra("pos", pos)
                .setClass
                        (getActivity(), DetailActivity.class).putExtra("title", listEntities.get
                        (pos).getTitle());
        startActivity(intent);
    }
}
