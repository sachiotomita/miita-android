package com.naoto.yamaguchi.miita.api;

import android.os.Handler;
import android.os.Looper;

/**
 * Package Private.
 * Thread Handler.
 *
 * Created by naoto on 2016/10/12.
 */

final class ThreadUtil {

  /**
   * Main Thread.
   */
  public static void executeOnMainThread(Runnable runnable) {
    Handler handler = new Handler(Looper.getMainLooper());
    handler.post(runnable);
  }

  /**
   * Worker Thread
   */
  public static void executeOnWorkerThread(Runnable runnable) {
    Thread thread = new Thread(runnable);
    thread.start();
  }

  private ThreadUtil() {}
}
