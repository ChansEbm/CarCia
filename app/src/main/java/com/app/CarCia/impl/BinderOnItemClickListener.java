package com.app.CarCia.impl;

import android.view.View;

//BinderAdapter适配点击事件
public interface BinderOnItemClickListener {
    void onBinderItemClick(View view, int pos);

    void onBinderItemLongClick(View view, int pos);
}
