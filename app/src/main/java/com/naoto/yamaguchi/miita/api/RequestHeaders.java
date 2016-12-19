package com.naoto.yamaguchi.miita.api;

import com.naoto.yamaguchi.miita.oauth.CurrentUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Request HTTP headers.
 * <p>
 * Created by naoto on 2016/10/09.
 */

/**
 * FIXME:
 * Request HeaderもRequestType<T>の
 * interfaceに加えることで疎結合にしたい
 */
final class RequestHeaders {

    private static final String AUTH_KEY = "Authorization";
    private static final String AUTH_VALUE = "Bearer ";

    private final Map<String, String> headers;
    private final CurrentUser currentUser;

    public RequestHeaders() {
        this.headers = new HashMap<>();
        this.currentUser = CurrentUser.getInstance();
    }

    public void setHeaderParameter(String key, String value) {
        this.headers.put(key, value);
    }

    public Map<String, String> toMap() {
        this.setAuthorization();
        return this.headers;
    }

    private void setAuthorization() {
        if (this.currentUser.isAuthorize()) {
            String token = this.currentUser.getToken();
            this.headers.put(AUTH_KEY, AUTH_VALUE + token);
        }
    }
}
