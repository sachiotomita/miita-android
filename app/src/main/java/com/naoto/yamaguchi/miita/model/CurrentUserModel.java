package com.naoto.yamaguchi.miita.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.entity.User;
import com.naoto.yamaguchi.miita.model.base.BaseNoObjectModel;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.service.AuthUserService;
import com.naoto.yamaguchi.miita.service.AuthorizeService;

/**
 * Created by naoto on 16/06/30.
 */
public final class CurrentUserModel extends BaseNoObjectModel<Void> {

    private AuthorizeService authorizeService;
    private AuthUserService authUserService;
    private CurrentUser currentUser;

    public CurrentUserModel(Context context) {
        super(context);
        this.authorizeService = new AuthorizeService(this.context);
        this.authUserService = new AuthUserService(this.context);
        this.currentUser = CurrentUser.getInstance();
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

    public void request(String code, OnModelListener<Void> listener) {
        super.addModelListener(listener);
        this.authorizeRequest(code);
    }

    private void authorizeRequest(String code) {
        this.authorizeService.request(code, new AuthorizeService.OnRequestListener() {
            @Override
            public void onSuccess(String token) {
                currentUser.setToken(context, token);
                authUserRequest();
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }

    private void authUserRequest() {
        this.authUserService.request(new AuthUserService.OnRequestListener() {
            @Override
            public void onSuccess(User user) {
                currentUser.setID(context, user.getId());
                currentUser.setImageUrl(context, user.getImageUrlString());
                deliverSuccess();
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }
}
