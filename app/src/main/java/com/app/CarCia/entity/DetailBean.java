package com.app.CarCia.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by ChanZeeBm on 2015/11/30.
 */
public class DetailBean extends BaseObservable {
    private String url;
    private String spec;
    private String no;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Bindable
    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    @Bindable
    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }
}
