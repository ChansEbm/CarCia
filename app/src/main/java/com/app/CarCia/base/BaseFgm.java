package com.app.CarCia.base;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.app.CarCia.adapters.CommonBinderAdapter;
import com.app.CarCia.adapters.MultiAdapter;
import com.app.CarCia.impl.BinderOnItemClickListener;
import com.app.CarCia.tools.TitleBarTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChanZeeBm on 2015/9/7.
 */
public abstract class BaseFgm<T> extends Fragment implements View.OnClickListener,
        BinderOnItemClickListener {
    protected CommonBinderAdapter<T> commonBinderAdapter;
    protected MultiAdapter<T> multiAdapter;
    protected List<T> list = new ArrayList<>();
    protected ViewDataBinding viewDataBinding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(isHasOptionsMenu());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (isHasOptionsMenu())
            inflater.inflate(menuRes(), menu);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(getActivity()),
                getContentView(), null, false);
        initViews();
        initEvents();
        return viewDataBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    protected abstract void initViews();

    protected abstract void initEvents();

    protected abstract void onClick(int id, View view);

    protected abstract int getContentView();


    @Override
    public void onClick(View v) {
        int id = v.getId();
        onClick(id, v);
    }

    protected void start(Bundle bundle, Class<?> targetClz) {
        start(new Intent().setClass(getActivity(), targetClz).putExtras(bundle));
    }

    protected void start(Class<?> targetClz, Integer... flags) {
        Intent intent = new Intent().setClass(getActivity(), targetClz);
        for (Integer flag : flags) {
            intent.addFlags(flag);
        }
        start(intent);
    }

    protected TitleBarTools titleBarTools(Toolbar toolbar) {
        return new TitleBarTools(this, toolbar);
    }

    protected void start(Class<?> cls) {
        start(new Intent().setClass(getActivity(), cls));
    }

    private void start(Intent intent) {
        startActivity(intent);
    }

    protected View getViewById(int id) {
        return viewDataBinding.getRoot().findViewById(id);
    }

    @Override
    public void onBinderItemClick(View view, int pos) {

    }

    @Override
    public void onBinderItemLongClick(View view, int pos) {

    }

    public abstract boolean isHasOptionsMenu();

    public abstract int menuRes();
}
