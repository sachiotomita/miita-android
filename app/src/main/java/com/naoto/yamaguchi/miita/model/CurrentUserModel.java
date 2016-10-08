package com.naoto.yamaguchi.miita.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.naoto.yamaguchi.miita.ex_api.APIException;
import com.naoto.yamaguchi.miita.entity.User;
import com.naoto.yamaguchi.miita.model.base.BaseModel;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.model.base.RequestType;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.service.AuthUserService;
import com.naoto.yamaguchi.miita.service.AuthorizeService;
import com.naoto.yamaguchi.miita.service.base.OnRequestListener;

/**
 * Created by naoto on 16/06/30.
 */
public final class CurrentUserModel extends BaseModel<Void> {

    private String code;
    private AuthorizeService authorizeService;
    private AuthUserService authUserService;
    private CurrentUser currentUser;

    public CurrentUserModel(Context context) {
        super(context);
        this.authorizeService = new AuthorizeService(this.context);
        this.authUserService = new AuthUserService(this.context);
        this.currentUser = CurrentUser.getInstance();
    }

    public CurrentUserModel setCode(String code) {
        this.code = code;
        return this;
    }

    public boolean isExistCodeQuery(Intent intent) {
        Uri uri = intent.getData();

        if (uri == null) {
            return false;
        }

        String code = uri.getQueryParameter("code");
        if (code.isEmpty()) {
            return false;
        }

        return true;
    }

    public String getCodeQuery(Intent intent) {
        Uri uri = intent.getData();
        return uri.getQueryParameter("code");
    }

    @Override
    protected boolean isListView() {
        return false;
    }

    public void request(OnModelListener<Void> listener) {
        this.request(null, listener);
    }

    @Override
    protected void serviceRequest(RequestType type) {
        this.authorizeService.request(this.code, new OnRequestListener<String>() {
            @Override
            public void onSuccess(String results) {
                currentUser.setToken(context, results);
                authUserRequest();
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }

    private void authUserRequest() {
        this.authUserService.request(new OnRequestListener<User>() {
            @Override
            public void onSuccess(User results) {
                currentUser.setID(context, results.getId());
                currentUser.setImageUrl(context, results.getImageUrlString());
                deliverSuccess(null);
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }

    @Override
    protected Void processResults(RequestType type, Void results) {
        return null;
    }
}
