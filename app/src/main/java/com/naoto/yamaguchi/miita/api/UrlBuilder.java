package com.naoto.yamaguchi.miita.api;

import android.support.annotation.NonNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Request URL Builder.
 * <p>
 * Created by naoto on 2016/10/15.
 */

final class UrlBuilder {

    private static final String ENTRY_POINT = "https://qiita.com/api/v2";

    private StringBuilder builder;
    private Map<String, String> params;

    public UrlBuilder() {
        this.builder = new StringBuilder(ENTRY_POINT);
        this.params = new HashMap<>();
    }

    public String build() {
        String urlString = this.builder.toString() + this.buildQuery();
        this.clean();
        return urlString;
    }

    private void clean() {
        this.builder = new StringBuilder(ENTRY_POINT);
        if (this.params != null) {
            this.params.clear();
        }
    }

    public UrlBuilder setPath(@NonNull String path) {
        this.builder.append(path);
        return this;
    }

    public UrlBuilder setParams(@NonNull Map<String, String> params) {
        this.params = params;
        return this;
    }

    private String buildQuery() {
        final StringBuilder queryBuilder = new StringBuilder();

        if (this.params == null) {
            return "";
        }

        for (Map.Entry<String, String> entry : this.params.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                String key = entry.getKey();
                String value = entry.getValue();
                String query = key + "=" + value;

                if (queryBuilder.toString().isEmpty()) {
                    queryBuilder.append("?" + query);
                } else {
                    queryBuilder.append("&" + query);
                }
            }
        }

        return queryBuilder.toString();
    }
}
