package com.naoto.yamaguchi.miita.api;

/**
 * HTTP Response.
 * <p>
 * Created by naoto on 2016/10/09.
 */

public final class Response<T> {

    private final ResponseHeaders headers;
    private final String rawBody;
    private final T result;

    public Response(ResponseHeaders headers, String rawBody, T result) {
        this.headers = headers;
        this.rawBody = rawBody;
        this.result = result;
    }

    public int code() {
        return this.headers.getStatusCode();
    }

    public boolean isRelNext() {
        return this.headers.isRelNext();
    }

    public String rawBody() {
        return this.rawBody;
    }

    public T result() {
        return this.result;
    }
}
