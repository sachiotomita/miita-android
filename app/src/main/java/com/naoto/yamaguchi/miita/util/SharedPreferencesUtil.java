package com.naoto.yamaguchi.miita.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by naoto on 16/08/31.
 */
public final class SharedPreferencesUtil {

    private static final String SERVICE_KEY = "com.naoto.yamaguchi.Miita";

    private SharedPreferencesUtil() {}

    public static String getString(Context context, String key, String defaultValue) {
        return context
                .getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE)
                .getString(key, defaultValue);
    }

    public static void setString(Context context, String key, String value) {
        context.getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE)
                .edit()
                .putString(key, value)
                .apply();
    }

    public static void deleteString(Context context, String key) {
        context.getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE)
                .edit()
                .remove(key)
                .apply();
    }

    public static boolean getBool(Context context, String key, boolean defaultValue) {
        return context
                .getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE)
                .getBoolean(key, defaultValue);
    }

    public static void setBool(Context context, String key, boolean value) {
        context.getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE)
                .edit()
                .putBoolean(key, value)
                .apply();
    }

    public static void deleteBool(Context context, String key) {
        context.getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE)
                .edit()
                .remove(key)
                .apply();
    }
}
