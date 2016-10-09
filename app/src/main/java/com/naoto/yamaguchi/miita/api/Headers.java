package com.naoto.yamaguchi.miita.api;

import android.content.Context;

/**
 * Base http header class.
 *
 * Created by naoto on 2016/10/09.
 */

public abstract class Headers {

  private final Context context;

  public Headers(Context context) {
    this.context = context;
  }
}
