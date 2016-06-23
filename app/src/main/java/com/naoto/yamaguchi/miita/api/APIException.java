package com.naoto.yamaguchi.miita.api;

/**
 * Created by naoto on 16/06/23.
 */

// TODO: errorのtypeを判別したい
public final class APIException extends Exception {

    public APIException(String detailMessage) {
        super(detailMessage);
    }

}
