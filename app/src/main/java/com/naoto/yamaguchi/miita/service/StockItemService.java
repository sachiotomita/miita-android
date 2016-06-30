package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.api.APIURLBuilder;
import com.naoto.yamaguchi.miita.entity.StockItem;
import com.naoto.yamaguchi.miita.mapper.ItemListObjectMapper;

import java.util.List;

/**
 * Created by naoto on 16/06/30.
 */
public final class StockItemService extends DeliverResponseService<List<StockItem>> {

    public interface OnRequestListener {
        void onSuccess(List<StockItem> results);
        void onError(APIException e);
    }

    private int page;
    private String userId;
    private OnRequestListener listener;

    public StockItemService(Context context) {
        super(context);
        this.page = 1;
    }

    @Override
    protected String getMethod() {
        return "GET";
    }

    @Override
    protected String getUrlString() {
        return APIURLBuilder.build(this.getPath(), this.page);
    }

    @Override
    protected byte[] getBody() {
        return null;
    }

    @Override
    protected String getPath() {
        return "/users/" + this.userId + "/stocks";
    }

    public void request(int page, String userId, OnRequestListener listener) {
        this.page = page;
        this.userId = userId;
        this.addRequestListener(listener);
        super.request();
    }

    private void addRequestListener(OnRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<StockItem> getResponse(String jsonString) throws APIException {
        try {
            return ItemListObjectMapper.map(jsonString, StockItem.class);
        } catch (APIException e) {
            throw e;
        }
    }

    @Override
    protected void deliverSuccess(List<StockItem> results) {
        if (this.listener != null) {
            this.listener.onSuccess(results);
        }
    }

    @Override
    protected void deliverError(APIException e) {
        if (this.listener != null) {
            this.listener.onError(e);
        }
    }
}
