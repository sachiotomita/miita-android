package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.ex_api.APIException;
import com.naoto.yamaguchi.miita.entity.Item;
import com.naoto.yamaguchi.miita.mapper.ItemListObjectMapper;
import com.naoto.yamaguchi.miita.service.base.BaseService;
import com.naoto.yamaguchi.miita.service.base.OnRequestListener;

import java.util.List;

/**
 * Created by naoto on 16/07/17.
 */
public final class TagItemService extends BaseService<List<Item>> {

    private String tagId;

    public TagItemService(Context context) {
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
        return "/tags/" + this.tagId + "/items";
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
    protected List<Item> getResponse(String json) throws APIException {
        try {
            return ItemListObjectMapper.map(json, Item.class);
        } catch (APIException e) {
            throw e;
        }
    }

    public void request(int page, String tagId, OnRequestListener<List<Item>> listener) {
        this.page = page;
        this.tagId = tagId;
        super.request(listener);
    }
}
