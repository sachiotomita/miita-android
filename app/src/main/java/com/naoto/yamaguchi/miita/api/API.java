package com.naoto.yamaguchi.miita.api;

/**
 * API Client.
 *
 * Created by naoto on 2016/10/08.
 */

public class API {
  private static API instance = null;
  private final ConnectionExecutor executor;

  private API() {
    this.executor = new ConnectionExecutor();
  }

  public static synchronized API getInstance() {
    if (instance == null) {
      instance = new API();
    }
    return instance;
  }

  public static synchronized <T> void request(RequestType<T> type, Callback<T> callback) {
    getInstance().p_request(type, callback);
  }

  private <T> void p_request(RequestType<T> type, Callback<T> callback) {
    this.executor.execute(type, callback);
  }
}
