package com.naoto.yamaguchi.miita.ex_api;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by naoto on 16/06/25.
 */
public final class APIConfig {

    private String method;
    private byte[] body;
    private String urlString;

    public APIConfig() {}

    public String getMethod() {
        return this.method;
    }

    public void setMethod(@NonNull String method) {
        this.method = method;
    }

    public byte[] getBody() {
        return this.body;
    }

    public void setBody(@Nullable byte[] body) {
        this.body = body;
    }

    public String getUrlString() {
        return this.urlString;
    }

    public void setUrlString(@NonNull String urlString) {
        this.urlString = urlString;
    }
}
