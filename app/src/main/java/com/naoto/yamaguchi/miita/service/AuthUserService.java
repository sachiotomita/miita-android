package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.api.APIURLBuilder;
import com.naoto.yamaguchi.miita.entity.User;
import com.naoto.yamaguchi.miita.mapper.UserObjectMapper;

import org.json.JSONException;

/**
 * Created by naoto on 16/07/01.
 */
public class AuthUserService extends DeliverResponseService<User> {

    public interface OnRequestListener {
        void onSuccess(User user);
        void onError(APIException e);
    }

    private OnRequestListener listener;

    public AuthUserService(Context context) {
        super(context);
    }

    @Override
    protected String getMethod() {
        return "GET";
    }

    @Override
    protected String getUrlString() {
        return APIURLBuilder.build(this.getPath());
    }

    @Override
    protected byte[] getBody() {
        return null;
    }

    @Override
    protected String getPath() {
        return "/authenticated_user";
    }

    public void request(OnRequestListener listener) {
        this.addRequestListener(listener);
        super.request();
    }

    private void addRequestListener(OnRequestListener listener) {
        this.listener = listener;
    }

    @Override
    protected User getResponse(String jsonString) throws APIException {
        try {
            return UserObjectMapper.map(jsonString);
        } catch (JSONException e) {
            throw new APIException(e.toString());
        }
    }

    @Override
    protected void deliverSuccess(User results) {
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
