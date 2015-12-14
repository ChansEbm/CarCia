package com.app.CarCia.ui.Activity;

import android.content.Intent;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.CarCia.OrderCateLayout;
import com.app.CarCia.R;
import com.app.CarCia.adapters.CommonBinderAdapter;
import com.app.CarCia.adapters.CommonBinderHolder;
import com.app.CarCia.base.BaseAty;
import com.app.CarCia.databinding.ItemOrderCateLayout;
import com.app.CarCia.entity.OrderCateBean;
import com.app.CarCia.model.NetworkModel;
import com.app.CarCia.tools.AppTools;
import com.app.CarCia.tools.LogTools;

public class OrderCateActivity extends BaseAty<OrderCateBean.ListEntity> {

    private OrderCateLayout chooseType;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        chooseType = (OrderCateLayout) viewDataBinding;
    }

    @Override
    protected void initViews() {
        defaultTitleBar(this).setTitle(R.string.order_cate);
        recyclerView = chooseType.recyclerView;

        commonBinderAdapter = new CommonBinderAdapter<OrderCateBean.ListEntity>(this, R.layout
                .item_order_cate, list) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, OrderCateBean.ListEntity listEntity) {
                LogTools.i(listEntity.getName());
                ((ItemOrderCateLayout) viewDataBinding).setType(listEntity);
            }
        };
    }

    @Override
    protected void initEvents() {
        commonBinderAdapter.setBinderOnItemClickListener(this);
        recyclerView.setAdapter(commonBinderAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(AppTools.defaultHorizontalDecoration());

        new NetworkModel(this).orderCate(commonBinderAdapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_choose_type;
    }

    @Override
    protected void onClick(int id, View view) {
        if (id == R.id.nav) {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @Override
    public void onBinderItemClick(View view, int pos) {
        String cateId = list.get(pos).getID();
        String cate = list.get(pos).getName();
        Intent intent = new Intent().putExtra("cateId", cateId).putExtra("cate", cate);

        setResult(RESULT_OK, intent);
        finish();
    }
}
