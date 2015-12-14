package com.app.CarCia.dialog;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.app.CarCia.R;
import com.app.CarCia.impl.DialListener;
import com.app.CarCia.tools.AppTools;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * 打电话dialog
 * Created by ChanZeeBm on 2015/10/30.
 */
public class DialDialog {
    private MaterialDialog dialog;
    private Context context;
    private DialListener listener;

    public DialDialog(Context context, DialListener listener) {
        this.context = context;
        this.listener = listener;
        dialog = new MaterialDialog(context);
        dialog.setCanceledOnTouchOutside(true);
    }

    public void dial(@NonNull final String phoneNum) {
        dialog.setTitle(R.string.dial_dialog).setMessage(context.getResources().getString(R
                .string.dialog_content) + phoneNum).setPositiveButton(R.string.positive, new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                AppTools.CALL(phoneNum);
                if (listener != null) {
                    listener.dial();
                }
                dialog.dismiss();
            }
        }).setNegativeButton(R.string.negative, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
