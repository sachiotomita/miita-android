package com.naoto.yamaguchi.miita.api;

/**
 * API HTTP Exception.
 * <p>
 * Created by naoto on 2016/10/11.
 */

// FIXME: handle error type
public final class HttpException extends Exception {
    public HttpException(String message) {
        super(message);
    }
}
