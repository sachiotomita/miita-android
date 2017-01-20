package com.naoto.yamaguchi.miita.util.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.naoto.yamaguchi.miita.application.MiitaContext;

/**
 * Request item per page.
 * Default 30.
 *
 * Created by naoto on 2016/09/25.
 */

public final class PerPage {
    public static String get() {
        return getSharedPreferences().getString(
                PreferencesConstants.PER_PAGE_KEY,
                PreferencesConstants.PER_PAGE_DEFAULT_VALUE);
    }

    public static void set(String value) {
        getSharedPreferences()
                .edit()
                .putString(PreferencesConstants.PER_PAGE_KEY, value)
                .apply();
    }

    public static void delete() {
        getSharedPreferences()
                .edit()
                .remove(PreferencesConstants.PER_PAGE_KEY)
                .apply();
    }

    // NOTE: get value from PreferenceScreen.
    private static SharedPreferences getSharedPreferences() {
        final Context context = MiitaContext.getInstance().getContext();
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
