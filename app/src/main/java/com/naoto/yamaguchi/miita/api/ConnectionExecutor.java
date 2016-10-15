package com.naoto.yamaguchi.miita.api;

import android.content.Context;

/**
 * Connection Executor.
 * use ConnectionRunnable.
 *
 * Created by naoto on 2016/10/15.
 */

final class ConnectionExecutor<T> {

  private final Context context;
  private final RequestHeaders requestHeaders;

  public ConnectionExecutor(Context context) {
    this.context = context;
    this.requestHeaders = new RequestHeaders(context);
  }

  public void send(RequestType<T> type, Callback<T> callback) {

  }

  private void buildUrlString() {

  }

  private void buildBody() {

  }
}
