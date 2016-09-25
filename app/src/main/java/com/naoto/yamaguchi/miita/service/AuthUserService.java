package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.entity.User;
import com.naoto.yamaguchi.miita.mapper.UserObjectMapper;
import com.naoto.yamaguchi.miita.service.base.BaseService;

import org.json.JSONException;

/**
 * Created by naoto on 16/07/01.
 */
public class AuthUserService extends BaseService<User> {

    public AuthUserService(Context context) {
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
        return "/authenticated_user";
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
    protected User getResponse(String json) throws APIException {
        try {
            return UserObjectMapper.map(json);
        } catch (JSONException e) {
            throw new APIException(e.toString());
        }
    }

    public void request(OnRequestListener listener) {
        super.request(listener);
    }
}
