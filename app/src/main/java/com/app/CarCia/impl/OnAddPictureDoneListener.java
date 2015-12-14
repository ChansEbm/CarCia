package com.app.CarCia.impl;

import android.view.View;

/**
 * Created by ChanZeeBm on 2015/11/19.
 */
public interface OnAddPictureDoneListener {
    void onAddPictureDone(View picParentView, int childViewCount);

    void onDeletePictureDone(int childViewCount);
}
