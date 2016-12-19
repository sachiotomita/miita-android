package com.naoto.yamaguchi.miita.service;

import com.naoto.yamaguchi.miita.api.HttpException;
import com.naoto.yamaguchi.miita.api.Method;
import com.naoto.yamaguchi.miita.api.RequestType;
import com.naoto.yamaguchi.miita.entity.AllItem;
import com.naoto.yamaguchi.miita.mapper.ItemListObjectMapper;
import com.naoto.yamaguchi.miita.util.preference.PerPage;

import org.json.JSONException;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Request All Item.
 * GET: v2/items/
 * <p>
 * Created by naoto on 16/06/29.
 */
public final class AllItemService implements RequestType<List<AllItem>> {

    private int page;

    public AllItemService() {
        this.page = 1;
    }

    public AllItemService setPage(int page) {
        this.page = page;
        return this;
    }

    @Override
    public Method getMethod() {
        return Method.GET;
    }

    @Override
    public String getPath() {
        return "/items";
    }

    @Override
    public Map<String, String> getParameters() {
        return new HashMap<String, String>() {
            {
                put("page", Integer.toString(page));
                put("per_page", PerPage.get());
            }
        };
    }

    @Override
    public List<AllItem> processResponse(String response) throws HttpException {
        try {
            return ItemListObjectMapper.map(response, AllItem.class);
        } catch (JSONException | IllegalAccessException | InstantiationException |
                ParseException e) {
            return null;
        }
    }
}
