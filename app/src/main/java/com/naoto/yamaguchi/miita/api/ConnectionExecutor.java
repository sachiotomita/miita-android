package com.naoto.yamaguchi.miita.api;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Connection Executor.
 * use ConnectionRunnable.
 *
 * Created by naoto on 2016/10/15.
 */

// TODO: rename ConnectionAdapter.
final class ConnectionExecutor {

  private final RequestHeaders requestHeaders;

  public ConnectionExecutor() {
    this.requestHeaders = new RequestHeaders();
  }

  public <T> void execute(RequestType<T> type, Callback<T> callback) {
    Method method = type.getMethod();
    String urlString = this.buildUrlString(type);
    byte[] body = this.buildBody(type);

    final ConnectionRunnable<T> runnable = new ConnectionRunnable<>(method, urlString,
            this.requestHeaders, body, type, callback);

    ThreadUtil.executeOnWorkerThread(runnable);
  }

  private <T> String buildUrlString(RequestType<T> type) {
    final UrlBuilder urlBuilder = new UrlBuilder();

    Method method = type.getMethod();
    String path = type.getPath();
    Map<String, String> params = type.getParameters();

    if (method == Method.POST || method == Method.PUT) {
      return urlBuilder
              .setPath(path)
              .build();
    } else {
      return urlBuilder
              .setPath(path)
              .setParams(params)
              .build();
    }
  }

  private <T> byte[] buildBody(RequestType<T> type) {
    Method method = type.getMethod();
    Map<String, String> params = type.getParameters();

    if (params == null || params.isEmpty()) {
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
