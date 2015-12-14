package com.app.CarCia.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.app.CarCia.R;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by ChanZeeBm on 2015/12/12.
 */
public class ShareDialog extends Dialog {

    public ShareDialog(Context context) {
        super(context, R.style.dialogDefaultStyle);
        TextView textView = new TextView(context);
        textView.setText("123");
        new MaterialDialog(context).setView(textView).show();
    }
}
