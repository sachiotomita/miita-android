package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.API;
import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.api.APIListener;
import com.naoto.yamaguchi.miita.api.Response;

/**
 * Created by naoto on 16/06/25.
 */
public abstract class NoResponseService extends QiitaService {

    abstract void deliverSuccess();
    abstract void deliverError(APIException e);

    public NoResponseService(Context context) {
        super(context);
    }

    protected void request() {
        API.request(this.context, this.buildAPIConfig(), new APIListener() {
            @Override
            public void onSuccess(Response response) {
                deliverSuccess();
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }
}
