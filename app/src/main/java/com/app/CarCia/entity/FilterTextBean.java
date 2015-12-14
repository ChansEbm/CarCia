package com.app.CarCia.entity;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

/**
 * Created by ChanZeeBm on 2015/11/27.
 */
public class FilterTextBean extends BaseObservable {
    private String text;
    private int textColor;

    @Bindable
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Bindable
    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
