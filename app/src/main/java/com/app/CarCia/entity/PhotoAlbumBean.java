package com.app.CarCia.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by ChanZeeBm on 2015/12/2.
 */
public class PhotoAlbumBean extends BaseObservable {
    private String title;
    private String filePath;
    private String firstUri;
    private int count;

    public PhotoAlbumBean(String title, String filePath, String firstUri, int count) {
        this.title = title;
        this.filePath = filePath;
        this.firstUri = firstUri;
        this.count = count;
    }

    @Bindable
    public String getTitle() {
        return title;
    }

    @Bindable
    public String getFirstUri() {
        return firstUri;
    }

    @Bindable
    public int getCount() {
        return count;
    }

    @Bindable
    public String getFilePath() {
        return filePath;
    }
}

