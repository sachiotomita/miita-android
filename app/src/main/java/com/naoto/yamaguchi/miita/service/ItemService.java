package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.api.APIURLBuilder;
import com.naoto.yamaguchi.miita.service.base.BaseService;

/**
 * Created by naoto on 16/08/15.
 */
public final class ItemService extends BaseService<Void> {

    private String HTTPMethod;
    private String itemId;

    public ItemService(Context context) {
        super(context);
    }

    @Override
    protected String getMethod() {
        return this.HTTPMethod;
    }

    @Override
    protected byte[] getBody() {
        return null;
    }

    @Override
    protected String getPath() {
        return "/items/" + this.itemId + "/stock";
    }

    @Override
    protected int getPage() {
        return NO_PAGE_VALUE;
    }

    @Override
    protected boolean isPerPage() {
        return false;
    }

    @Override
    protected boolean isResponse() {
        return false;
    }

    @Override
    protected Void getResponse(String json) throws APIException {
        return null;
    }

    public void request(String method, String itemId, OnRequestListener listener) {
        this.HTTPMethod = method;
        this.itemId = itemId;
        super.request(listener);
    }
}
