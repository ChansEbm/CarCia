package com.app.CarCia.tools;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.app.CarCia.R;

/**
 * Created by ChanZeeBm on 2015/10/13.
 */
public class SnakeTools {
    private Snackbar snackbar;

    public static SnakeTools getInstance() {
        return SingleSnake.singleBar;
    }

    public void showSnackBar(Context context, View view, CharSequence charSequence) {
        snackbar = Snackbar.make(view, charSequence, Snackbar.LENGTH_LONG);
        initSnackBarBackground(context);
        snackbar.show();
    }

    public void showSnackBarWithAction(Context context, View view, CharSequence charSequence,
                                       CharSequence actionCharSequence, View.OnClickListener
                                               listener) {
        snackbar = Snackbar.make(view, charSequence, Snackbar.LENGTH_LONG).setAction
                (actionCharSequence, listener).setActionTextColor(context.getResources().getColor
                (R.color.color_snack_bar_action_text_color));
        initSnackBarBackground(context);
        snackbar.show();
    }

    private void initSnackBarBackground(Context context) {
        snackbar.getView().setBackgroundColor(context.getResources().getColor(R.color
                .color_snack_bar_background));
    }

    private static class SingleSnake {
        private static final SnakeTools singleBar = new SnakeTools();
    }
}
