package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.API;
import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.api.APIListener;
import com.naoto.yamaguchi.miita.api.Response;

/**
 * Created by naoto on 16/06/25.
 */
public abstract class DeliverResponseService<T> extends QiitaService {

    abstract T getResponse(String jsonString) throws APIException;
    abstract void deliverSuccess(T results);
    abstract void deliverError(APIException e);

    public DeliverResponseService(Context context) {
        super(context);
    }

    protected void request() {
        API.request(this.context, this.buildAPIConfig(), new APIListener() {
            @Override
            public void onSuccess(Response response) {
                String jsonString = response.getBody();

                try {
                    T results = getResponse(jsonString);
                    deliverSuccess(results);
                } catch (APIException e) {
                    deliverError(e);
                }
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }
}
