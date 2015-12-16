package com.app.CarCia.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.app.CarCia.tools.LogTools;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ChanZeeBm on 2015/12/16.
 */
public class JpushReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        String title = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        LogTools.w(intent.getAction());
        LogTools.i(title);
        LogTools.v(extras);
    }
}
