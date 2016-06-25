package com.naoto.yamaguchi.miita.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by naoto on 16/06/22.
 */
public final class ThreadUtil {

    private ThreadUtil() {}

    public static void execute(ThreadType type, Runnable runnable) {
        switch (type) {
            case MAIN:
                executeOnMainThread(runnable);
                break;
            case WORKER:
                executeOnWorkerThread(runnable);
                break;
        }
    }

    public static void executeOnMainThread(Runnable runnable) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }

    public static void executeOnWorkerThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }
}