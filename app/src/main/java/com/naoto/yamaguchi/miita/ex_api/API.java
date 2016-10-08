package com.naoto.yamaguchi.miita.ex_api;

import android.content.Context;

import com.naoto.yamaguchi.miita.ex_api.APIConfig;
import com.naoto.yamaguchi.miita.ex_api.APIListener;
import com.naoto.yamaguchi.miita.ex_api.APIRunnable;
import com.naoto.yamaguchi.miita.util.thread.ThreadType;
import com.naoto.yamaguchi.miita.util.thread.ThreadUtil;

/**
 * Created by naoto on 16/06/25.
 */
public final class API {

    private API() {}

    public static void request(Context context, APIConfig config, APIListener listener) {
        String method = config.getMethod();
        String urlString = config.getUrlString();
        byte[] body = config.getBody();

        APIRunnable runnable = new APIRunnable(
                context,
                urlString,
                method,
                body,
                listener
        );

        ThreadUtil.execute(ThreadType.WORKER, runnable);
    }
}
