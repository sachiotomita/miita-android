package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.api.APIURLBuilder;

/**
 * Created by naoto on 16/08/15.
 */
public final class ItemService extends NoResponseService {

    public interface OnRequestListener {
        void onSuccess();
        void onError(APIException e);
    }

    private String HTTPMethod;
    private String itemId;
    private OnRequestListener listener;

    public ItemService(Context context) {
        super(context);
    }

    @Override
    protected String getMethod() {
        return this.HTTPMethod;
    }

    @Override
    protected String getUrlString() {
        return APIURLBuilder.build(this.getPath());
    }

    @Override
    protected byte[] getBody() {
        return null;
    }

    @Override
    protected String getPath() {
        return "/items/" + this.itemId + "/stock";
    }

    public void request(String method, String itemId, OnRequestListener listener) {
        this.HTTPMethod = method;
        this.itemId = itemId;
        this.addRequestListener(listener);
        super.request();
    }

    private void addRequestListener(OnRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected void deliverSuccess() {
        if (this.listener != null) {
            this.listener.onSuccess();
        }
    }

    @Override
    protected void deliverError(APIException e) {
        if (this.listener != null) {
            this.listener.onError(e);
        }
    }
}
