package com.naoto.yamaguchi.miita.api;

import android.content.Context;

/**
 * API Client.
 *
 * Created by naoto on 2016/10/08.
 */

public class API {
  public static <T> void request(Context context, RequestType<T> type, Callback<T> callback) {
    final ConnectionExecutor<T> executor = new ConnectionExecutor<>(context);
    executor.execute(type, callback);
  }
}
