package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.entity.AllItem;
import com.naoto.yamaguchi.miita.mapper.ItemListObjectMapper;
import com.naoto.yamaguchi.miita.service.base.BaseService;
import com.naoto.yamaguchi.miita.service.base.OnRequestListener;

import java.util.List;

/**
 * Created by naoto on 16/06/29.
 */
public final class AllItemService extends BaseService<List<AllItem>> {

    public AllItemService(Context context) {
        super(context);
        this.page = 1;
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
        return "/items";
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
    protected List<AllItem> getResponse(String json) throws APIException {
        try {
            return ItemListObjectMapper.map(json, AllItem.class);
        } catch (APIException e) {
            throw e;
        }
    }

    public void request(int page, OnRequestListener<List<AllItem>> listener) {
        this.page = page;
        super.request(listener);
    }
}
