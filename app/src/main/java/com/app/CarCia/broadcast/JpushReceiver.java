package com.app.CarCia.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import com.app.CarCia.impl.JPushListener;
import com.app.CarCia.tools.LogTools;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by ChanZeeBm on 2015/12/16.
 */
public class JPushReceiver extends BroadcastReceiver {
    private JPushListener jPushListener;

    public void setjPushListener(JPushListener jPushListener) {
        this.jPushListener = jPushListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (jPushListener != null)
            jPushListener.jPush(intent);
        LogTools.i(intent.getAction());
    }
}
