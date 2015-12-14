package com.app.CarCia.widget.PopupWindow;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.app.CarCia.PopupFilter;
import com.app.CarCia.R;
import com.app.CarCia.adapters.CommonBinderAdapter;
import com.app.CarCia.adapters.CommonBinderHolder;
import com.app.CarCia.databinding.ItemPopFilterLayout;
import com.app.CarCia.entity.HomeBean;
import com.app.CarCia.impl.BinderOnItemClickListener;
import com.app.CarCia.impl.FilterPerform;
import com.app.CarCia.impl.OnFilterSelectedListener;
import com.app.CarCia.tools.WrappableGridLayoutManager;
import com.google.repacked.antlr.v4.runtime.tree.ErrorNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChanZeeBm on 2015/11/27.
 */
public class FilterPopupWindow extends BasePopupWindow implements BinderOnItemClickListener {

    private PopupFilter popupFilter;
    private RecyclerView recyclerView;
    private CommonBinderAdapter<HomeBean.ListEntity> adapter;
    private List<HomeBean.ListEntity> filterTextBeans = new ArrayList<>();
    private OnFilterSelectedListener onFilterSelectedListener;

    private FilterPerform perform;

    public FilterPopupWindow(Context context) {
        super(context);
        popupFilter = (PopupFilter) viewDataBinding;
        recyclerView = popupFilter.recyclerView;

        filterTextBeans = new ArrayList<>();

        adapter = new CommonBinderAdapter<HomeBean.ListEntity>(context, R.layout
                .item_popup_filter_text,
                filterTextBeans) {
            @Override
            public void onBind(ViewDataBinding viewDataBinding, CommonBinderHolder holder, int
                    position, HomeBean.ListEntity listEntity) {
                ItemPopFilterLayout itemPopFilterLayout = ((ItemPopFilterLayout) viewDataBinding);
                itemPopFilterLayout.text.setChecked(listEntity.isCheck());
                itemPopFilterLayout.setItem(listEntity);
            }
        };
        adapter.setBinderOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new WrappableGridLayoutManager(context, 3));
        setAnimationStyle(-1);
    }

    @Override
    public int getPopupLayout() {
        return R.layout.popup_filter;
    }

    public void setShowData(FilterPerform perform, List<HomeBean.ListEntity> filterTextBeans) {

        if (this.perform != perform) {
            this.filterTextBeans.clear();
            HomeBean.ListEntity entity = new HomeBean.ListEntity(false);
            entity.setTitle("不限");
            this.filterTextBeans.addAll(filterTextBeans);
            this.filterTextBeans.add(0, entity);
            adapter.notifyDataSetChanged();
            this.perform = perform;
        }
    }

    public void setOnFilterSelectedListener(OnFilterSelectedListener onFilterSelectedListener) {
        this.onFilterSelectedListener = onFilterSelectedListener;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onBinderItemClick(View view, int pos) {
        resetAllChecked();
        filterTextBeans.get(pos).setIsCheck(true);
        adapter.notifyItemChanged(pos);
        if (onFilterSelectedListener != null) {
            if (pos == 0) {
                onFilterSelectedListener.onFilterSelected(null, perform);
            } else {
                onFilterSelectedListener.onFilterSelected(filterTextBeans.get(pos), null);
            }
        }
        dismiss();
    }

    @Override
    public void onBinderItemLongClick(View view, int pos) {

    }

    private void resetAllChecked() {
        for (HomeBean.ListEntity listEntity : filterTextBeans) {
            listEntity.setIsCheck(false);
        }
        adapter.notifyDataSetChanged();
    }
}
