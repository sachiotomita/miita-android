package com.naoto.yamaguchi.miita.util.thread;

import android.os.Handler;
import android.os.Looper;

import com.naoto.yamaguchi.miita.util.thread.ThreadType;

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

    private static void executeOnMainThread(Runnable runnable) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(runnable);
    }

    private static void executeOnWorkerThread(Runnable runnable) {
        Thread thread = new Thread(runnable);
        thread.start();
    }
}
