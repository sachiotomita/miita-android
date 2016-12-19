package com.naoto.yamaguchi.miita.api;

import android.content.Context;

import java.util.Map;

/**
 * Response http header class.
 * <p>
 * Created by naoto on 2016/10/09.
 */

public final class ResponseHeaders extends Headers {

    private static final int DEFAULT_CODE_VALUE = 0;

    private int statusCode;
    private boolean relNext;
    private Map<String, String> rawHeaders;

    public ResponseHeaders() {
        this.statusCode = DEFAULT_CODE_VALUE;
        this.relNext = false;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int code) {
        this.statusCode = code;
    }

    public boolean isRelNext() {
        return this.relNext;
    }

    public void setRelNext(boolean relNext) {
        this.relNext = relNext;
    }

    public Map<String, String> getRawHeaders() {
        return this.rawHeaders;
    }

    public void setRawHeaders(Map<String, String> rawHeaders) {
        this.rawHeaders = rawHeaders;
    }
}
