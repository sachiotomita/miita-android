package com.naoto.yamaguchi.miita.oauth;

import android.content.Context;
import android.text.TextUtils;

import com.naoto.yamaguchi.miita.util.preference.SharedPreferencesUtil;

/**
 * Created by naoto on 16/06/24.
 */
public final class AccessToken {

    private static final String TOKEN_KEY = "Qiita.AccessToken";
    private static final String DEFAULT_VALUE = "";

    public static boolean isExist(Context context) {
        if (TextUtils.isEmpty(getToken(context))) {
            return false;
        }

        return true;
    }

    public static String getToken(Context context) {
        return SharedPreferencesUtil.getString(context, TOKEN_KEY, DEFAULT_VALUE);
    }

    public static void setToken(Context context, String token) {
        SharedPreferencesUtil.setString(context, TOKEN_KEY, token);
    }

    public static void deleteToken(Context context) {
        SharedPreferencesUtil.deleteString(context, TOKEN_KEY);
    }
}
