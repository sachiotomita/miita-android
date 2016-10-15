package com.naoto.yamaguchi.miita.api;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Connection Executor.
 * use ConnectionRunnable.
 *
 * Created by naoto on 2016/10/15.
 */

final class ConnectionExecutor<T> {

  private final Context context;
  private final RequestHeaders requestHeaders;
  private final UrlBuilder urlBuilder;

  public ConnectionExecutor(Context context) {
    this.context = context;
    this.requestHeaders = new RequestHeaders(context);
    this.urlBuilder = new UrlBuilder();
  }

  public void execute(RequestType<T> type, Callback<T> callback) {
    Method method = type.getMethod();
    String urlString = this.buildUrlString(type);
    byte[] body = this.buildBody(type);

    final ConnectionRunnable<T> runnable = new ConnectionRunnable<>(method, urlString,
            this.requestHeaders, body, type, callback);

    ThreadUtil.executeOnWorkerThread(runnable);
  }

  private String buildUrlString(RequestType<T> type) {
    Method method = type.getMethod();
    String path = type.getPath();
    Map<String, String> params = type.getParameters();

    if (method == Method.POST || method == Method.PUT) {
      return this.urlBuilder
              .setPath(path)
              .build();
    } else {
      return this.urlBuilder
              .setPath(path)
              .setParams(params)
              .build();
    }
  }

  private byte[] buildBody(RequestType<T> type) {
    Method method = type.getMethod();
    Map<String, String> params = type.getParameters();

    if (params.isEmpty()) {
      return null;
    }

    if (method == Method.POST || method == Method.PUT) {
      try {
        JSONObject object = new JSONObject();
        for (Map.Entry<String, String> entry: params.entrySet()) {
          String key = entry.getKey();
          String value = entry.getValue();

          if (key.isEmpty() || value.isEmpty()) {
            continue;
          }

          object.put(key, value);
        }

        return object.toString().getBytes();
      } catch (JSONException e) {
        return null;
      }
    } else  {
      return null;
    }
  }
}
