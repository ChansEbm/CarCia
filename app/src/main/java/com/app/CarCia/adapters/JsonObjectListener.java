package com.app.CarCia.adapters;

import com.app.CarCia.impl.OkHttpResponseListener;
import com.app.CarCia.tools.LogTools;

import java.util.List;

/**
 * Created by ChanZeeBm on 2015/12/4.
 */
public abstract class JsonObjectListener<T> implements OkHttpResponseListener<T> {

    @Override
    public void onError(String error) {
        LogTools.e(error);
    }

    @Override
    public void onJsonArrayResponse(List t) {

    }

}
