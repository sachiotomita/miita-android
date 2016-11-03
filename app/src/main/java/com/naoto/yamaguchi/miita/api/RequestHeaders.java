package com.naoto.yamaguchi.miita.api;

import android.content.Context;

import com.naoto.yamaguchi.miita.application.MiitaContext;
import com.naoto.yamaguchi.miita.oauth.CurrentUser;

import java.util.HashMap;
import java.util.Map;

/**
 * Request HTTP headers.
 *
 * Created by naoto on 2016/10/09.
 */

final class RequestHeaders {

  private static final String AUTH_KEY = "Authorization";
  private static final String AUTH_VALUE = "Bearer ";

  private final Context context;
  private final Map<String, String> headers;
  private final CurrentUser currentUser;

  public RequestHeaders() {
    this.context = MiitaContext.getInstance().getContext();
    this.headers = new HashMap<>();
    this.currentUser = CurrentUser.getInstance();
  }

  public void setHeaderParameter(String key, String value) {
    this.headers.put(key, value);
  }

  public Map<String, String> toMap() {
    this.setAuthorization();
    return this.headers;
  }

  private void setAuthorization() {
    if (this.currentUser.isAuthorize(this.context)) {
      String token = this.currentUser.getToken(this.context);
      this.headers.put(AUTH_KEY, AUTH_VALUE + token);
    }
  }
}
