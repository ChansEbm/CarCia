package com.app.CarCia.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by ChanZeeBm on 2015/12/7.
 */
public class RecorderBean extends BaseObservable {
    private int index;
    private String filePath;
    private String title;

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
