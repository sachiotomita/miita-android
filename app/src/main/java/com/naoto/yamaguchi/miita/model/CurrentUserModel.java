package com.naoto.yamaguchi.miita.model;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.entity.User;
import com.naoto.yamaguchi.miita.oauth.AccessToken;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.service.AuthUserService;
import com.naoto.yamaguchi.miita.service.AuthorizeService;
import com.naoto.yamaguchi.miita.util.ThreadType;
import com.naoto.yamaguchi.miita.util.ThreadUtil;

/**
 * Created by naoto on 16/06/30.
 */
public class CurrentUserModel {

    public interface OnRequestListener {
        void onSuccess();
        void onError(APIException e);
    }

    private Context context;
    private String code;
    private OnRequestListener listener;
    private AuthorizeService authorizeService;
    private AuthUserService authUserService;
    private CurrentUser currentUser;

    public CurrentUserModel(Context context) {
        this.context = context;
        this.authorizeService = new AuthorizeService(this.context);
        this.authUserService = new AuthUserService(this.context);
        this.currentUser = CurrentUser.getInstance();
    }

    public void request(String code, OnRequestListener listener) {
        this.addRequestListener(listener);
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

    private void addRequestListener(OnRequestListener listener) {
        this.listener = listener;
    }

    private void deliverSuccess() {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                listener.onSuccess();
            }
        });
    }

    private void deliverError(final APIException e) {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                listener.onError(e);
            }
        });
    }
}
