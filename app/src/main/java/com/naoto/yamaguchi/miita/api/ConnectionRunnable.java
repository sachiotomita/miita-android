package com.naoto.yamaguchi.miita.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Running HTTP connection in worker thread.
 *
 * Created by naoto on 2016/10/10.
 */

public final class ConnectionRunnable<T> implements Runnable {

  private final Connection connection;
  private final String urlString;
  private final Method method;
  private final RequestHeaders headers;
  private final byte[] body;
  private final RequestType<T> requestType;
  private final Callback<T> callback;

  public ConnectionRunnable(Method method, String urlString, RequestHeaders headers, byte[] body,
                            RequestType<T> requestType, Callback<T> callback) {
    this.connection = new Connection();
    this.method = method;
    this.urlString = urlString;
    this.headers = headers;
    this.body = body;
    this.requestType = requestType;
    this.callback = callback;
  }

  @Override
  public void run() {
    // connection build
    try {
      this.connection.build(this.urlString, this.method, this.headers, this.body);
    } catch (HttpException e) {
      this.callFailure(e);
      return;
    }

    // http request start
    try {
      this.connection.connect();
    } catch (HttpException e) {
      this.callFailure(e);
      return;
    }

    // status code
    int code = 0;
    try {
      code = this.connection.getStatusCode();
    } catch (HttpException e) {
      this.callFailure(e);
      return;
    }

    // TODO: code check.

    // response headers
    Map<String, String> rawHeaders = this.connection.getResponseHeaders();
    // TODO: use parse class
    final ResponseHeaders responseHeaders = new ResponseHeaders();
    responseHeaders.setStatusCode(code);
    responseHeaders.setRawHeaders(rawHeaders);

    // response body
    String rawBody = null;
    try {
      rawBody = this.connection.getRawBody();
    } catch (HttpException e) {
      this.callFailure(e);
      return;
    }

    // response processing
    T result = null;
    try {
      String body = this.connection.getRawBody();
      if (body != null) {
        result = this.requestType.processResponse(body);
      }
    } catch (HttpException e) {
      this.callFailure(e);
      return;
    }

    Response<T> response = new Response<>(responseHeaders, rawBody, result);
    this.callSuccess(response);
  }

  private void callSuccess(final Response<T> response) {
    ThreadUtil.executeOnMainThread(new Runnable() {
      @Override
      public void run() {
        if (callback != null) {
          callback.onResponse(response);
        }
      }
    });
  }

  private void callFailure(final HttpException e) {
    ThreadUtil.executeOnMainThread(new Runnable() {
      @Override
      public void run() {
        if (callback != null) {
          callback.onFailure(e);
        }
      }
    });
  }
}
