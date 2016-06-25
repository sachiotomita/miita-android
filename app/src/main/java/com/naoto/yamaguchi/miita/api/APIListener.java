package com.naoto.yamaguchi.miita.api;

/**
 * Created by naoto on 16/06/25.
 */
public interface APIListener {
    void onSuccess(Response response);
    void onError(APIException e);
}
