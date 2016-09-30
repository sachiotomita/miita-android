package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.model.base.BaseModel;
import com.naoto.yamaguchi.miita.model.base.BaseNoObjectModel;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.service.ItemService;
import com.naoto.yamaguchi.miita.service.base.OnRequestListener;

/**
 * Created by naoto on 16/08/15.
 */
public final class ItemModel extends BaseModel<Void> {
    
    public enum Type {
        CHECK {
            @Override
            public String toMethod() {
                return "GET";
            }
        },
        STOCK {
            @Override
            public String toMethod() {
                return "PUT";
            }
        },
        UNSTOCK {
            @Override
            public String toMethod() {
                return "DELETE";
            }
        };

        public abstract String toMethod();
    }

    private ItemService service;
    private String itemId;
    private Type type;
    private boolean isStock;

    public ItemModel(Context context) {
        super(context);
        this.service = new ItemService(context);
        this.itemId = null;
        this.type = Type.CHECK;
        this.isStock = false;
    }

    public ItemModel setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    public ItemModel setType(Type type) {
        this.type = type;
        return this;
    }

    public boolean getIsStock() {
        return this.isStock;
    }

    @Override
    protected boolean isListView() {
        return false;
    }

    synchronized public void request(OnModelListener<Void> listener) {
        this.request(null, listener);
    }

    @Override
    protected void serviceRequest(RequestType type) {
        final String method = this.type.toMethod();
        final OnRequestListener<Void> listener = this.getListener(this.type);
        this.service.request(method, this.itemId, listener);
    }

    private OnRequestListener<Void> getListener(Type type) {
        switch (type) {
            case CHECK:
                return this.getCheckListener();
            case STOCK:
                return this.getStockListener();
            case UNSTOCK:
                return this.getUnStockListener();
        }
    }

    private OnRequestListener<Void> getCheckListener() {
        return new OnRequestListener<Void>() {
            @Override
            public void onSuccess(Void results) {
                isStock = true;
                deliverSuccess(null);
            }

            @Override
            public void onError(APIException e) {
                isStock = false;
                deliverError(e);
            }
        };
    }

    private OnRequestListener<Void> getStockListener() {
        return new OnRequestListener<Void>() {
            @Override
            public void onSuccess(Void results) {
                isStock = true;
                deliverSuccess(null);
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        };
    }

    private OnRequestListener<Void> getUnStockListener() {
        return new OnRequestListener<Void>() {
            @Override
            public void onSuccess(Void results) {
                isStock = false;
                deliverSuccess(null);
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        };
    }

    @Override
    protected Void processResults(RequestType type, Void results) {
        return null;
    }
}
