package com.app.CarCia.tools;

import android.app.Activity;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by ChanZeeBm on 2015/9/7.
 */
public class SharedPreferencesTools {
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferencesTools(Activity activity) {
        sharedPreferences = activity.getSharedPreferences("digital", Activity.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public SharedPreferencesTools putString(String key, String value) {
        editor.putString(key, value);
        return this;
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "null");
    }

    public SharedPreferencesTools putInt(String key, int value) {
        editor.putInt(key, value);
        return this;
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, -1001);
    }

    public SharedPreferencesTools putSet(String key, Set<String> values) {
        editor.putStringSet(key, values);
        return this;
    }

    public Set<String> getSet(String key) {
        return sharedPreferences.getStringSet(key, new HashSet<String>());
    }

    public void commit() {
        editor.commit();
    }
}
