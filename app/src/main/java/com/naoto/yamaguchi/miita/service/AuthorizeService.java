package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.api.APIURLBuilder;
import com.naoto.yamaguchi.miita.mapper.AccessTokenObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by naoto on 16/06/30.
 */
public final class AuthorizeService extends DeliverResponseService<String> {

    public interface OnRequestListener {
        void onSuccess(String token);
        void onError(APIException e);
    }

    private String code;
    private OnRequestListener listener;

    public AuthorizeService(Context context) {
        super(context);
    }

    @Override
    protected String getMethod() {
        return "POST";
    }

    @Override
    protected String getUrlString() {
        return APIURLBuilder.build(this.getPath());
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

    public void request(String code, OnRequestListener listener) {
        this.code = code;
        this.addRequestListener(listener);
        super.request();
    }

    private void addRequestListener(OnRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected String getResponse(String jsonString) throws APIException {
        try {
            return AccessTokenObjectMapper.map(jsonString);
        } catch (APIException e) {
            throw e;
        }
    }

    @Override
    protected void deliverSuccess(String results) {
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
