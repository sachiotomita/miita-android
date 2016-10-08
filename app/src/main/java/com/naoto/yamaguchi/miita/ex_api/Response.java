package com.naoto.yamaguchi.miita.ex_api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by naoto on 16/06/23.
 */
public final class Response {

    private int statusCode;
    private Map<String, String> header;
    private String body;

    public Response() {
        this.statusCode = 0;
        this.header = new HashMap<>();
        this.body = null;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, String> getHeader() {
        return this.header;
    }

    public void setHeader(String key, String value) {
        this.header.put(key, value);
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
