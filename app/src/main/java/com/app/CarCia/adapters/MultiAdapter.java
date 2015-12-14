package com.app.CarCia.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by ChanZeeBm on 2015/11/11.
 */
public abstract class MultiAdapter<T> extends CommonBinderAdapter<T> {
    public static final int FIRST_LAYOUT = 0x1;
    public static final int SECOND_LAYOUT = 0x2;

    public MultiAdapter(Context context, List<T> list, Integer... layouts) {
        super(context, list, layouts);
    }

    @Override
    public CommonBinderHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = null;
        switch (viewType) {
            case FIRST_LAYOUT:
                viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()
                ), layouts[0], parent, false);
                break;
            case SECOND_LAYOUT:
                viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()
                ), layouts[1], parent, false);
                break;
        }
        holder = new CommonBinderHolder(viewDataBinding, viewDataBinding.getRoot(), listener);
        return holder;
    }

    @Override
    public abstract int getItemViewType(int position);

    public void setFirstLayout(int firstLayout) {
        layouts[0] = firstLayout;
    }

    public void setSecondLayout(int secondLayout) {
        layouts[1] = secondLayout;
    }
}
