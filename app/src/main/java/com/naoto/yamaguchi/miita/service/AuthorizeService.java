package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.mapper.AccessTokenObjectMapper;
import com.naoto.yamaguchi.miita.service.base.BaseService;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by naoto on 16/06/30.
 */
public final class AuthorizeService extends BaseService<String> {

    private String code;

    public AuthorizeService(Context context) {
        super(context);
    }

    @Override
    protected String getMethod() {
        return "POST";
    }

    // TODO: parameterを定数化
    @Override
    protected byte[] getBody() {
        try {
            JSONObject body = new JSONObject();
            body.put("client_id", "1f9862b42618ce3f030a4988215fb20befba3cfa");
            body.put("client_secret", "c298ca57963361cb4206dc1670e522d979a86b7b");
            body.put("code", this.code);
            return body.toString().getBytes();
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    protected String getPath() {
        return "/access_tokens";
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
        return true;
    }

    @Override
    protected String getResponse(String json) throws APIException {
        try {
            return AccessTokenObjectMapper.map(json);
        } catch (APIException e) {
            throw e;
        }
    }

    public void request(String code, OnRequestListener listener) {
        this.code = code;
        super.request(listener);
    }
}
