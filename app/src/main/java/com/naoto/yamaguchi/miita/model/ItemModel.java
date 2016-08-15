package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.service.ItemService;
import com.naoto.yamaguchi.miita.util.ThreadType;
import com.naoto.yamaguchi.miita.util.ThreadUtil;

/**
 * Created by naoto on 16/08/15.
 */
public final class ItemModel {

    public interface OnRequestListener {
        void onSuccess();
        void onError(APIException e);
        void onComplete();
    }

    private static final String GET = "GET";
    private static final String PUT = "PUT";
    private static final String DELETE = "DELETE";

    private Context context;
    private ItemService service;
    private OnRequestListener listener;
    private boolean isStock;

    public ItemModel(Context context) {
        this.context = context;
        this.service = new ItemService(context);
        this.isStock = false;
    }

    public boolean getIsStock() {
        return this.isStock;
    }

    public void check(String itemId, OnRequestListener listener) {
        this.addRequestListener(listener);
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

    public void stock(String itemId, OnRequestListener listener) {
        this.addRequestListener(listener);
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

    public void unstock(String itemId, OnRequestListener listener) {
        this.addRequestListener(listener);
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

    private void addRequestListener(OnRequestListener listener) {
        this.listener = listener;
    }

    private void send(String method, String itemId, ItemService.OnRequestListener listener) {
        this.service.request(method, itemId, listener);
    }

    private void deliverSuccess() {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                listener.onSuccess();
                listener.onComplete();
            }
        });
    }

    private void deliverError(final APIException e) {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                listener.onError(e);
                listener.onComplete();
            }
        });
    }
}
