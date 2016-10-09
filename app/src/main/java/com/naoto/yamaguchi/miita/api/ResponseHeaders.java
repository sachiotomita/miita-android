package com.naoto.yamaguchi.miita.api;

import android.content.Context;

/**
 * Response http header class.
 *
 * Created by naoto on 2016/10/09.
 */

public final class ResponseHeaders extends Headers {

  private static final int DEFAULT_CODE_VALUE = 0;

  private int statusCode;
  private boolean relNext;

  public ResponseHeaders(Context context) {
    super(context);
    this.statusCode = DEFAULT_CODE_VALUE;
    this.relNext = false;
  }

  public int getStatusCode() {
    return this.statusCode;
  }

  public void setStatusCode(int code) {
    this.statusCode = code;
  }

  public boolean isRelNext() {
    return this.relNext;
  }

  public void setRelNext(boolean relNext) {
    this.relNext = relNext;
  }
}
