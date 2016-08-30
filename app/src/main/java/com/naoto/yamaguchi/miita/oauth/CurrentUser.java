package com.naoto.yamaguchi.miita.oauth;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by naoto on 16/06/23.
 */
public final class CurrentUser {

    private static final String SERVICE_NAME = "Miita";
    private static final String ID_KEY = "Qiita.User.id";
    private static final String IMAGE_URL_KEY = "Qiita.User.imageUrl";
    private static final String DEFAULT_VALUE = "";

    private static CurrentUser instance = null;

    public static synchronized CurrentUser getInstance() {
        if (instance == null) {
            instance = new CurrentUser();
        }

        return instance;
    }

    private CurrentUser() {}

    public boolean isAuthorize(Context context) {
        return AccessToken.isExist(context);
    }

    public void logout(Context context) {
        AccessToken.deleteToken(context);
    }

    public String getToken(Context context) {
        return AccessToken.getToken(context);
    }

    public void setToken(Context context, String token) {
        AccessToken.setToken(context, token);
    }

    // TODO: sharedPreferencesのutil
    public String getID(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SERVICE_NAME, Context.MODE_PRIVATE);
        String id = sharedPreferences.getString(ID_KEY, DEFAULT_VALUE);
        return id;
    }

    public void setID(Context context, String id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SERVICE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ID_KEY, id);
        editor.apply();
    }

    public String getImageUrl(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SERVICE_NAME, Context.MODE_PRIVATE);
        String imageUrl = sharedPreferences.getString(IMAGE_URL_KEY, DEFAULT_VALUE);
        return imageUrl;
    }

    public void setImageUrl(Context context, String imageUrl) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SERVICE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(IMAGE_URL_KEY, imageUrl);
        editor.apply();
    }

}
