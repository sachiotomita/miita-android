package com.naoto.yamaguchi.miita.model;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.naoto.yamaguchi.miita.api.API;
import com.naoto.yamaguchi.miita.api.Callback;
import com.naoto.yamaguchi.miita.api.HttpException;
import com.naoto.yamaguchi.miita.api.Response;
import com.naoto.yamaguchi.miita.entity.User;
import com.naoto.yamaguchi.miita.model.base.OnModelListener;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;
import com.naoto.yamaguchi.miita.service.AuthUserService;
import com.naoto.yamaguchi.miita.service.AuthorizeService;
import com.naoto.yamaguchi.miita.util.exception.MiitaException;

/**
 * Current User Model.
 *
 * Created by naoto on 16/06/30.
 */
public final class CurrentUserModel {

  private final Context context;
  private final AuthorizeService authorizeService;
  private final AuthUserService authUserService;
  private final CurrentUser currentUser;
  private OnModelListener<User> listener;

  // TODO: 2通り用意する
  // 1. token -> user
  // 2. user

  public CurrentUserModel(Context context) {
    this.context = context;
    this.authorizeService = new AuthorizeService();
    this.authUserService = new AuthUserService();
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

  public void request(String code, OnModelListener<User> listener) {
    this.listener = listener;
    this.authorizeService.setCode(code);
    API.request(this.authorizeService, new Callback<String>() {
      @Override
      public void onResponse(Response<String> response) {
        String token = response.result();
        currentUser.setToken(token);
        getAuthUserRequest();
      }

      @Override
      public void onFailure(HttpException e) {
        callError(e);
      }
    });
  }

  private void getAuthUserRequest() {
    API.request(this.authUserService, new Callback<User>() {
      @Override
      public void onResponse(Response<User> response) {
        User user = response.result();
        currentUser.setID(user.getId());
        currentUser.setImageUrl(user.getImageUrlString());
        callSuccess(response);
      }

      @Override
      public void onFailure(HttpException e) {
        callError(e);
      }
    });
  }

  private void callSuccess(Response<User> response) {
    if (this.listener != null) {
      this.listener.onSuccess(response.result());
      this.listener.onComplete();
    }
  }

  private void callError(HttpException e) {
    MiitaException exception = new MiitaException(e.getMessage());
    if (this.listener != null) {
      this.listener.onError(exception);
      this.listener.onComplete();
    }
  }
}
