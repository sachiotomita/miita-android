package com.naoto.yamaguchi.miita.service;

import com.naoto.yamaguchi.miita.api.HttpException;
import com.naoto.yamaguchi.miita.api.Method;
import com.naoto.yamaguchi.miita.api.RequestType;

import java.util.Map;

/**
 * Request CheckStock/Stock/UnStock.
 * GET, POST, DELETE: v2/items/__item_id__/stock
 * <p>
 * Created by naoto on 16/08/15.
 */
public final class ItemService implements RequestType<Void> {

    private Method method;
    private String itemId;

    public ItemService() {
    }

    public ItemService setMethod(Method method) {
        this.method = method;
        return this;
    }

    public ItemService setItemId(String itemId) {
        this.itemId = itemId;
        return this;
    }

    @Override
    public Method getMethod() {
        return this.method;
    }

    @Override
    public String getPath() {
        return "/items/" + this.itemId + "/stock";
    }

    @Override
    public Map<String, String> getParameters() {
        return null;
    }

    @Override
    public Void processResponse(String response) throws HttpException {
        return null;
    }
}
