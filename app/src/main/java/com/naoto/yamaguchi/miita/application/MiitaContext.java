package com.naoto.yamaguchi.miita.application;

import android.content.Context;

/**
 * Provide Application Context.
 * <p>
 * Created by naoto on 2016/11/03.
 */

public final class MiitaContext {
    private static MiitaContext instance = null;
    private Context context;

    static void onCreateApplication(Context context) {
        instance = new MiitaContext(context);
    }

    private MiitaContext(Context context) {
        this.context = context;
    }

    public static MiitaContext getInstance() {
        if (instance == null) {
            throw new RuntimeException("MiitaContext should be initialized!");
        }
        return instance;
    }

    public Context getContext() {
        return this.context;
    }
}
