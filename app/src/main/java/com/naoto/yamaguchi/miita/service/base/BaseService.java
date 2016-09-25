package com.naoto.yamaguchi.miita.service.base;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIConfig;
import com.naoto.yamaguchi.miita.api.APIURLBuilder;

/**
 * Created by naoto on 2016/09/25.
 */

public abstract class BaseService {
    protected static final int NO_PAGE_VALUE = 0;

    protected final Context context;
    protected final APIURLBuilder urlBuilder;

    protected int page;

    protected abstract String getMethod();
    protected abstract byte[] getBody();
    protected abstract String getPath();
    protected abstract int getPage();
    protected abstract boolean getPerPage();

    public BaseService(Context context) {
        this.context = context;
        this.urlBuilder = new APIURLBuilder(context);
    }

    protected APIConfig buildConfig() {
        APIConfig config = new APIConfig();
        config.setMethod(this.getMethod());
        config.setBody(this.getBody());

        this.urlBuilder.setPath(this.getPath());

        if (this.getPage() != NO_PAGE_VALUE) {
            this.urlBuilder.setPage(this.getPage());
        }

        if (this.getPerPage()) {
            this.urlBuilder.setPerPage();
        }

        config.setUrlString(this.urlBuilder.build());
        return config;
    }

}
