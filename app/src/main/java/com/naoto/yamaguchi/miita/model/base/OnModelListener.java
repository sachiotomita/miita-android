package com.naoto.yamaguchi.miita.model.base;

import com.naoto.yamaguchi.miita.api.APIException;

/**
 * Created by naoto on 16/09/22.
 */
public interface OnModelListener<T> {
    void onSuccess(T results);
    void onError(APIException e);
    void onComplete();
}
