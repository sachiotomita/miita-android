package com.naoto.yamaguchi.miita.ex_api;

import android.content.Context;

import com.naoto.yamaguchi.miita.oauth.CurrentUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by naoto on 16/06/23.
 */
public final class APIHeader {

    private static final String AUTHORIZATION_KEY = "Authorization";
    private static final String AUTHORIZATION_VALUE = "Bearer ";

    private Context context;
    private Map<String, String> header;
    private CurrentUser currentUser;

    public APIHeader(Context context) {
        this.context = context;
        this.header = new HashMap<>();
        this.currentUser = CurrentUser.getInstance();
        this.setAuthorizationToken();
    }

    public Map<String, String> getHeader() {
        return this.header;
    }

    private void setAuthorizationToken() {
        if (this.currentUser.isAuthorize(this.context)) {
            String token = this.currentUser.getToken(this.context);
            this.header.put(
                    AUTHORIZATION_KEY,
                    AUTHORIZATION_VALUE + token
            );
        }
    }
}
