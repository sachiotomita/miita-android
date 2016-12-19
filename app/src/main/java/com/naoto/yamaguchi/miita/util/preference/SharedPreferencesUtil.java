package com.naoto.yamaguchi.miita.util.preference;

import android.content.Context;

import com.naoto.yamaguchi.miita.application.MiitaContext;

/**
 * SharedPreference Wrapper class.
 * <p>
 * Created by naoto on 16/08/31.
 */

public final class SharedPreferencesUtil {

    private static final String SERVICE_KEY = PreferencesConstants.SERVICE_KEY;

    private SharedPreferencesUtil() {
    }

    private static Context getContext() {
        return MiitaContext.getInstance().getContext();
    }

    public static String getString(String key, String defaultValue) {
        return getContext()
                .getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE)
                .getString(key, defaultValue);
    }

    public static void setString(String key, String value) {
        getContext().getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE)
                .edit()
                .putString(key, value)
                .apply();
    }

    public static void deleteString(String key) {
        getContext().getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE)
                .edit()
                .remove(key)
                .apply();
    }

    public static boolean getBool(String key, boolean defaultValue) {
        return getContext()
                .getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE)
                .getBoolean(key, defaultValue);
    }

    public static void setBool(String key, boolean value) {
        getContext().getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(key, value)
                .apply();
    }

    public static void deleteBool(String key) {
        getContext().getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE)
                .edit()
                .remove(key)
                .apply();
    }
}
