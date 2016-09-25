package com.naoto.yamaguchi.miita.util.preference;

import android.content.Context;

/**
 * Created by naoto on 2016/09/25.
 */

public final class PerPage {
    public static String get(Context context) {
        return SharedPreferencesUtil.getString(
                context,
                PreferencesConstants.PER_PAGE_KEY,
                PreferencesConstants.PER_PAGE_DEFAULT_VALUE);
    }

    public static void set(Context context, String value) {
        SharedPreferencesUtil.setString(
                context,
                PreferencesConstants.PER_PAGE_KEY,
                value);
    }

    public static void delete(Context context) {
        SharedPreferencesUtil.deleteString(context, PreferencesConstants.PER_PAGE_KEY);
    }
}
