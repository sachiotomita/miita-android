package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.api.APIURLBuilder;
import com.naoto.yamaguchi.miita.entity.AllItem;
import com.naoto.yamaguchi.miita.mapper.ItemListObjectMapper;

import java.util.List;

/**
 * Created by naoto on 16/06/29.
 */
public final class AllItemService extends DeliverResponseService<List<AllItem>> {

    public interface OnRequestListener {
        void onSuccess(List<AllItem> result);
        void onError(APIException e);
    }

    private int page;
    private OnRequestListener listener;

    public AllItemService(Context context) {
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
        return "/items";
    }

    public void request(int page, OnRequestListener listener) {
        this.page = page;
        this.addRequestListener(listener);
        super.request();
    }

    private void addRequestListener(OnRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<AllItem> getResponse(String jsonString) throws APIException {
        try {
            return ItemListObjectMapper.map(jsonString, AllItem.class);
        } catch (APIException e) {
            throw e;
        }
    }

    @Override
    protected void deliverSuccess(List<AllItem> results) {
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
