package com.naoto.yamaguchi.miita.util;

import android.util.Log;

/**
 * Created by naoto on 16/06/23.
 */
public final class Logger {

    private static Logger instance = null;
    private static final String TAG = "Miita";
    private int logLevel;

    public static synchronized Logger getInstance() {
        if (instance == null) {
            instance = new Logger();
        }

        return instance;
    }

    private Logger() {
        this.logLevel = Log.INFO;
    }

    public void setLogLevel(int logLevel) {
        this.logLevel = logLevel;
    }

    public void verbose(String message) {
        if (this.logLevel > Log.VERBOSE) return;
        Log.v(TAG, message);
    }

    public void debug(String message) {
        if (this.logLevel > Log.DEBUG) return;
        Log.d(TAG, message);
    }

    public void info(String message) {
        if (this.logLevel > Log.INFO) return;
        Log.i(TAG, message);
    }

    public void warn(String message) {
        if (this.logLevel > Log.WARN) return;
        Log.w(TAG, message);
    }

    public void error(String message) {
        if (this.logLevel > Log.ERROR) return;
        Log.e(TAG, message);
    }

}
