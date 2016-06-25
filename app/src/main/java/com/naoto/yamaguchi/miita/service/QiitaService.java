package com.naoto.yamaguchi.miita.service;

import android.content.Context;

import com.naoto.yamaguchi.miita.api.APIConfig;

/**
 * Created by naoto on 16/06/25.
 */
public abstract class QiitaService {

    protected Context context;

    abstract String getMethod();
    abstract String getUrlString();
    abstract byte[] getBody();
    abstract String getPath();

    protected QiitaService(Context context) {
        this.context = context;
    }

    protected APIConfig buildAPIConfig() {
        APIConfig config = new APIConfig();
        config.setMethod(this.getMethod());
        config.setUrlString(this.getUrlString());
        config.setBody(this.getBody());
        return config;
    }
}
