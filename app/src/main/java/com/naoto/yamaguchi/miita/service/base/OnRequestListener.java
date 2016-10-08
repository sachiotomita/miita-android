package com.naoto.yamaguchi.miita.service.base;

import com.naoto.yamaguchi.miita.ex_api.APIException;

/**
 * Created by naoto on 2016/09/28.
 */

public interface OnRequestListener<T> {
    void onSuccess(T results);
    void onError(APIException e);
}
