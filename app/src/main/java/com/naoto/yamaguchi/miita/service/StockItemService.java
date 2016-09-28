package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.api.APIURLBuilder;
import com.naoto.yamaguchi.miita.entity.StockItem;
import com.naoto.yamaguchi.miita.mapper.ItemListObjectMapper;
import com.naoto.yamaguchi.miita.service.base.BaseService;
import com.naoto.yamaguchi.miita.service.base.OnRequestListener;

import java.util.List;

/**
 * Created by naoto on 16/06/30.
 */
public final class StockItemService extends BaseService<List<StockItem>> {

    private String userId;

    public StockItemService(Context context) {
        super(context);
    }

    @Override
    protected String getMethod() {
        return "GET";
    }

    @Override
    protected byte[] getBody() {
        return null;
    }

    @Override
    protected String getPath() {
        return "/users/" + this.userId + "/stocks";
    }

    @Override
    protected int getPage() {
        return this.page;
    }

    @Override
    protected boolean isPerPage() {
        return true;
    }

    @Override
    protected boolean isResponse() {
        return true;
    }

    @Override
    protected List<StockItem> getResponse(String json) throws APIException {
        try {
            return ItemListObjectMapper.map(json, StockItem.class);
        } catch (APIException e) {
            throw e;
        }
    }

    public void request(int page, String userId, OnRequestListener<List<StockItem>> listener) {
        this.page = page;
        this.userId = userId;
        super.request(listener);
    }
}
