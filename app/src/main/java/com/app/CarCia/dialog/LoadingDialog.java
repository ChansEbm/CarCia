package com.app.CarCia.dialog;

import android.app.Dialog;
import android.content.Context;

import com.app.CarCia.R;

/**
 * Created by ChanZeeBm on 2015/10/16.
 */
public class LoadingDialog extends Dialog {
    public LoadingDialog(Context context) {
        super(context, R.style.dialogDefaultStyle);
        setCancelable(true);
        setContentView(R.layout.dig_loading);
    }
}
