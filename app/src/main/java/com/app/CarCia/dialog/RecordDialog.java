package com.app.CarCia.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.app.CarCia.R;

/**
 * Created by ChanZeeBm on 2015/12/7.
 */
public class RecordDialog extends Dialog {
    public RecordDialog(Context context, int top, int left) {
        super(context, R.style.dialogDefaultStyle);
        setContentView(R.layout.dia_record);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        window.setGravity(Gravity.LEFT | Gravity.TOP);
        params.x = left;
        params.y = top;
        window.setAttributes(params);
    }

}
