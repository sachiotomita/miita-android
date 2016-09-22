package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.model.base.BaseNoObjectModel;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.service.ItemService;
import com.naoto.yamaguchi.miita.util.ThreadType;
import com.naoto.yamaguchi.miita.util.ThreadUtil;

/**
 * Created by naoto on 16/08/15.
 */
public final class ItemModel extends BaseNoObjectModel<Void> {

    private static final String GET = "GET";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    private ItemService service;
    private boolean isStock;

    public ItemModel(Context context) {
        super(context);
        this.service = new ItemService(context);
        this.isStock = false;
    }

    public boolean getIsStock() {
        return this.isStock;
    }

    public void check(String itemId, OnModelListener<Void> listener) {
        super.addModelListener(listener);
        this.send(GET, itemId, new ItemService.OnRequestListener() {
            @Override
            public void onSuccess() {
                isStock = true;
                deliverSuccess();
            }

            @Override
            public void onError(APIException e) {
                isStock = false;
                deliverError(e);
            }
        });
    }

    public void stock(String itemId, OnModelListener<Void> listener) {
        super.addModelListener(listener);
        this.send(PUT, itemId, new ItemService.OnRequestListener() {
            @Override
            public void onSuccess() {
                isStock = true;
                deliverSuccess();
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }

    public void unstock(String itemId, OnModelListener<Void> listener) {
        super.addModelListener(listener);
        this.send(DELETE, itemId, new ItemService.OnRequestListener() {
            @Override
            public void onSuccess() {
                isStock = false;
                deliverSuccess();
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }

    private void send(String method, String itemId, ItemService.OnRequestListener listener) {
        this.service.request(method, itemId, listener);
    }
}
