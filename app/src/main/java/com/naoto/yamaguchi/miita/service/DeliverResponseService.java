package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;

/**
 * Created by naoto on 16/06/25.
 */
public abstract class DeliverResponseService<T> extends QiitaService {

    abstract T getResponse(String jsonString) throws APIException;
    abstract void deliverSuccess(T results);
    abstract void deliverFailure(APIException e);

    public DeliverResponseService(Context context) {
        super(context);
    }

}
