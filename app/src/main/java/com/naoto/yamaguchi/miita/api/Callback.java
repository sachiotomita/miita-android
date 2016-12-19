package com.naoto.yamaguchi.miita.api;

/**
 * Callback from API class.
 * <p>
 * Created by naoto on 2016/10/10.
 */

public interface Callback<T> {

    void onResponse(Response<T> response);

    void onFailure(HttpException e);
}
