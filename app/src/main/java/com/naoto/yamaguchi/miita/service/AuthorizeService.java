package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.application.Constants;
import com.naoto.yamaguchi.miita.mapper.AccessTokenObjectMapper;
import com.naoto.yamaguchi.miita.service.base.BaseService;
import com.naoto.yamaguchi.miita.service.base.OnRequestListener;

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

    @Override
    protected byte[] getBody() {
        try {
            JSONObject body = new JSONObject();
            body.put(Constants.CLIENT_ID_KEY,
                    Constants.CLIENT_ID);
            body.put(Constants.CLIENT_SECRET_KEY,
                    Constants.CLIENT_SECRET);
            body.put(Constants.CODE_KEY, this.code);
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

    public void request(String code, OnRequestListener<String> listener) {
        this.code = code;
        super.request(listener);
    }
}
