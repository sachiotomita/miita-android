package com.naoto.yamaguchi.miita.api;

/**
 * Created by naoto on 16/06/25.
 */
public final class APIConfig {

    private String method;
    private String urlString;
    private byte[] body;

    public APIConfig() {}

    public String getMethod() {
        return this.method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrlString() {
        return this.urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public byte[] getBody() {
        return this.body;
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

}
