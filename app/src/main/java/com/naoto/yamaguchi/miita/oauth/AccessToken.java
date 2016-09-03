package com.naoto.yamaguchi.miita.oauth;

import android.content.Context;
import android.content.SharedPreferences;

import com.naoto.yamaguchi.miita.util.SharedPreferencesUtil;

/**
 * Created by naoto on 16/06/24.
 */
public final class AccessToken {

    private static final String SERVICE_NAME = "Miita";
    private static final String TOKEN_KEY = "Qiita.AccessToken";
    private static final String DEFAULT_VALUE = "";

    public static boolean isExist(Context context) {
        // TODO: TextUtil empty?
        if (getToken(context).length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String getToken(Context context) {
        return SharedPreferencesUtil.getString(context, TOKEN_KEY, DEFAULT_VALUE);
    }

    public static void setToken(Context context, String token) {
        SharedPreferencesUtil.setString(context, TOKEN_KEY, token);
    }

    public static void deleteToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SERVICE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(TOKEN_KEY);
        editor.apply();
    }
}
