package com.naoto.yamaguchi.miita.oauth;

import com.naoto.yamaguchi.miita.util.preference.SharedPreferencesUtil;

/**
 * Current Authorize User.
 * usage Singleton.
 * <p>
 * Created by naoto on 16/06/23.
 */
public final class CurrentUser {
    private static final String ID_KEY = "Qiita.User.id";
    private static final String NAME_KEY = "Qiita.User.name";
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

    public boolean isAuthorize() {
        return AccessToken.isExist();
    }

    public void logout() {
        AccessToken.delete();
    }

    public String getToken() {
        return AccessToken.get();
    }

    public void setToken(String token) {
        AccessToken.set(token);
    }

    public String getID() {
        return SharedPreferencesUtil.getString(ID_KEY, DEFAULT_VALUE);
    }

    public void setID(String id) {
        SharedPreferencesUtil.setString(ID_KEY, id);
    }

    public String getName() {
        return SharedPreferencesUtil.getString(NAME_KEY, DEFAULT_VALUE);
    }

    public void setName(String name) {
        SharedPreferencesUtil.setString(NAME_KEY, name);
    }

    public String getImageUrl() {
        return SharedPreferencesUtil.getString(IMAGE_URL_KEY, DEFAULT_VALUE);
    }

    public void setImageUrl(String imageUrl) {
        SharedPreferencesUtil.setString(IMAGE_URL_KEY, imageUrl);
    }
}
