package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.api.APIURLBuilder;
import com.naoto.yamaguchi.miita.entity.Item;
import com.naoto.yamaguchi.miita.mapper.ItemListObjectMapper;

import java.util.List;

/**
 * Created by naoto on 16/07/17.
 */
public final class TagItemService extends DeliverResponseService<List<Item>> {

    public interface OnRequestListener {
        void onSuccess(List<Item> results);
        void onError(APIException e);
    }

    private int page;
    private String tagId;
    private OnRequestListener listener;

    public TagItemService(Context context) {
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
        return "/tags/" + this.tagId + "/items";
    }

    public void request(int page, String tagId, OnRequestListener listener) {
        this.page = page;
        this.tagId = tagId;
        this.addRequestListener(listener);
        super.request();
    }

    private void addRequestListener(OnRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected List<Item> getResponse(String jsonString) throws APIException {
        try {
            return ItemListObjectMapper.map(jsonString, Item.class);
        } catch (APIException e) {
            throw e;
        }
    }

    @Override
    protected void deliverSuccess(List<Item> results) {
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
