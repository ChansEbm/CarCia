package com.app.CarCia.impl;

import java.util.List;

/**
 * Created by Administrator on 12/3/2015.
 */
public interface OkHttpResponseListener<T> {
    void onJsonObjectResponse(T t);

    void onJsonArrayResponse(List<T> t);

    void onError(String error);
}
