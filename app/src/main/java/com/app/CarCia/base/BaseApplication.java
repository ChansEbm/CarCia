package com.app.CarCia.base;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.support.multidex.MultiDex;

import com.app.CarCia.tools.AppTools;
import com.app.CarCia.tools.FileSaveTools;
import com.app.CarCia.tools.SharedPreferencesTools;
import com.baidu.mapapi.SDKInitializer;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import cn.sharesdk.framework.ShareSDK;


/**
 * Created by ChanZeeBm on 2015/9/7.
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        AppTools.init(this);
        SDKInitializer.initialize(this);
        MultiDex.install(this);
        FileSaveTools.getInstance().init(this);
        ShareSDK.initSDK(this);
        Logger.init("digital").setMethodCount(3).hideThreadInfo().setLogLevel
                (LogLevel.FULL);

    }

    public BaseApplication() {
        super();
    }

    public SharedPreferencesTools getSharedPreferences(Activity activity) {
        return new SharedPreferencesTools(activity);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

}
