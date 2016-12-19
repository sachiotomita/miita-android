package com.naoto.yamaguchi.miita.oauth;

import android.text.TextUtils;

import com.naoto.yamaguchi.miita.util.preference.SharedPreferencesUtil;

/**
 * API Access Token.
 * <p>
 * Created by naoto on 16/06/24.
 */

public final class AccessToken {
    private static final String TOKEN_KEY = "Qiita.AccessToken";
    private static final String DEFAULT_VALUE = "";

    public static boolean isExist() {
        if (TextUtils.isEmpty(get())) {
            return false;
        }
        return true;
    }

    public static String get() {
        return SharedPreferencesUtil.getString(TOKEN_KEY, DEFAULT_VALUE);
    }

    public static void set(String token) {
        SharedPreferencesUtil.setString(TOKEN_KEY, token);
    }

    public static void delete() {
        SharedPreferencesUtil.deleteString(TOKEN_KEY);
    }
}
