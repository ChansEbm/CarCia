package com.app.CarCia.base;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;

import com.app.CarCia.adapters.CommonBinderAdapter;
import com.app.CarCia.adapters.MultiAdapter;
import com.app.CarCia.impl.BinderOnItemClickListener;
import com.app.CarCia.tools.AppTools;
import com.app.CarCia.tools.SharedPreferencesTools;
import com.app.CarCia.tools.TitleBarTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChanZeeBm on 2015/9/7.
 */
public abstract class BaseAty<T> extends AppCompatActivity implements View.OnClickListener,
        BinderOnItemClickListener {

    private BaseApplication baseApplication;
    protected CommonBinderAdapter<T> commonBinderAdapter;
    protected MultiAdapter<T> multiAdapter;
    protected RecyclerView.LayoutManager layoutManager;
    protected List<T> list = new ArrayList<>();
    protected TitleBarTools titleBarTools;
    protected ViewDataBinding viewDataBinding;
    private boolean isFirstRunnable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            isFirstRunnable = savedInstanceState.getBoolean("isFirstRunnable");
        }
        if (getContentView() != 0) {
            viewDataBinding = DataBindingUtil.setContentView(this, getContentView());
        } else {
            throw new IllegalStateException("not invoke setContentView");
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //            getWindow().addFlags(WindowManager.LayoutParams
            // .FLAG_TRANSLUCENT_NAVIGATION);//虚拟底部
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//状态栏
        }
        baseApplication = (BaseApplication) getApplication();
        AppTools.addActivity(this);
    }


    @Override
    protected void onStart() {
        super.onStart();
        if (isFirstRunnable) {
            initViews();
            initEvents();
            isFirstRunnable = !isFirstRunnable;
        }
    }

    public SharedPreferencesTools getSharedPreferences(Activity activity) {
        return baseApplication.getSharedPreferences(activity);
    }

    protected TitleBarTools titleBarTools(AppCompatActivity activity) {
        titleBarTools = new TitleBarTools(activity);
        return titleBarTools;
    }

    protected TitleBarTools defaultTitleBar(AppCompatActivity activity) {
        titleBarTools = new TitleBarTools(activity);
        return titleBarTools.defaultToolBar(this);
    }


    protected void start(Bundle bundle, Class<?> targetClz) {
        start(new Intent().setClass(this, targetClz).putExtras(bundle));
    }

    protected void start(Class<?> targetClz, Integer... flags) {
        Intent intent = new Intent().setClass(this, targetClz);
        for (Integer flag : flags) {
            intent.addFlags(flag);
        }
        start(intent);
    }

    protected void start(Class<?> cls) {
        start(new Intent().setClass(this, cls));
    }

    private void start(Intent intent) {
        startActivity(intent);
    }


    protected abstract void initViews();

    protected abstract void initEvents();

    protected abstract int getContentView();

    protected abstract void onClick(int id, View view);

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putBoolean("isFirstRunnable", isFirstRunnable);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        onClick(id, v);
    }

    @Override
    public void onBinderItemClick(View view, int pos) {

    }

    @Override
    public void onBinderItemLongClick(View view, int pos) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppTools.loadingDialog = null;
    }
}


