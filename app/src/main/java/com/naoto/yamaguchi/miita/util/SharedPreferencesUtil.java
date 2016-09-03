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
        SharedPreferences sp =
                context.getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE);
        String value = sp.getString(key, defaultValue);
        return value;
    }

    public static void setString(Context context, String key, String value) {
        SharedPreferences sp =
                context.getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void deleteString(Context context, String key) {
        SharedPreferences sp =
                context.getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }

    public static boolean getBool(Context context, String key, boolean defaultValue) {
        SharedPreferences sp =
                context.getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE);
        boolean value = sp.getBoolean(key, defaultValue);
        return value;
    }

    public static void setBool(Context context, String key, boolean value) {
        SharedPreferences sp =
                context.getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void deleteBool(Context context, String key) {
        SharedPreferences sp =
                context.getSharedPreferences(SERVICE_KEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        editor.apply();
    }
}
