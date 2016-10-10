package com.naoto.yamaguchi.miita.api;

/**
 * Running HTTP connection in worker thread.
 *
 * Created by naoto on 2016/10/10.
 */

public final class ConnectionRunnable implements Runnable {

  private final Connection connection;

  // TODO: callBack class implement.
  public ConnectionRunnable(String urlString, Method method, RequestHeaders headers, byte[] body) {
    this.connection = new Connection();

  }

  @Override
  public void run() {

  }

  private void callSuccess() {

  }

  private void callFailure() {

  }
}
