package com.naoto.yamaguchi.miita.service.base;

import android.content.Context;

import com.naoto.yamaguchi.miita.ex_api.API;
import com.naoto.yamaguchi.miita.ex_api.APIConfig;
import com.naoto.yamaguchi.miita.ex_api.APIException;
import com.naoto.yamaguchi.miita.ex_api.APIListener;
import com.naoto.yamaguchi.miita.ex_api.APIURLBuilder;
import com.naoto.yamaguchi.miita.ex_api.Response;
import com.naoto.yamaguchi.miita.util.thread.ThreadType;
import com.naoto.yamaguchi.miita.util.thread.ThreadUtil;

/**
 * Created by naoto on 2016/09/25.
 */

public abstract class BaseService<T> {
    protected static final int NO_PAGE_VALUE = 0;

    protected final Context context;
    protected final APIURLBuilder urlBuilder;
    protected int page;
    protected OnRequestListener<T> listener;

    protected abstract String getMethod();
    protected abstract byte[] getBody();
    protected abstract String getPath();
    protected abstract int getPage();
    protected abstract boolean isPerPage();
    protected abstract boolean isResponse();
    protected abstract T getResponse(String json) throws APIException;

    public BaseService(Context context) {
        this.context = context;
        this.urlBuilder = new APIURLBuilder(context);
        this.page = 1;
    }

    protected void request(OnRequestListener<T> listener) {
        this.addRequestListener(listener);

        API.request(this.context, this.buildConfig(), new APIListener() {
            @Override
            public void onSuccess(Response response) {
                if (isResponse()) {
                    String json = response.getBody();
                    try {
                        T results = getResponse(json);
                        deliverSuccess(results);
                    } catch (APIException e) {
                        deliverError(e);
                    }
                } else {
                    deliverSuccess(null);
                }
            }

            @Override
            public void onError(APIException e) {
                deliverError(e);
            }
        });
    }

    protected void addRequestListener(OnRequestListener<T> listener) {
        this.listener = listener;
    }

    protected APIConfig buildConfig() {
        APIConfig config = new APIConfig();
        config.setMethod(this.getMethod());
        config.setBody(this.getBody());

        this.urlBuilder.setPath(this.getPath());

        if (this.getPage() != NO_PAGE_VALUE) {
            this.urlBuilder.setPage(this.getPage());
        }

        if (this.isPerPage()) {
            this.urlBuilder.setPerPage();
        }

        config.setUrlString(this.urlBuilder.build());
        return config;
    }

    protected void deliverSuccess(final T results) {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onSuccess(results);
                }
            }
        });
    }

    protected void deliverError(final APIException e) {
        ThreadUtil.execute(ThreadType.MAIN, new Runnable() {
            @Override
            public void run() {
                if (listener != null) {
                    listener.onError(e);
                }
            }
        });
    }
}
