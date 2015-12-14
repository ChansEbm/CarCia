package com.app.CarCia.tools;

import android.os.*;
import android.os.Process;
import android.support.v4.app.FragmentActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChanZeeBm on 2015/10/17.
 */
public class UITools {
    private static List<FragmentActivity> activities = new ArrayList<>();

    public static void removeAllActivities() {
        for (FragmentActivity activity : activities) {
            activity.finish();
            activities.remove(activity);
        }
        activities.clear();
        killProcress();
    }

    public static void removeSingleActivity(FragmentActivity fragmentActivity) {
        for (FragmentActivity activity : activities) {
            if (activities.contains(fragmentActivity)) {
                activities.remove(activity);
                break;
            }
        }
        fragmentActivity.finish();
        if (activities.isEmpty())
            killProcress();
    }

    public static void addActivity(FragmentActivity fragmentActivity) {
        if (!activities.contains(fragmentActivity))
            activities.add(fragmentActivity);
    }

    private static void killProcress() {
        android.os.Process.killProcess(Process.myPid());
    }
}
