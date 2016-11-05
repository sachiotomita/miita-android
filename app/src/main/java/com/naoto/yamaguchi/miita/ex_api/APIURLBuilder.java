package com.naoto.yamaguchi.miita.ex_api;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.naoto.yamaguchi.miita.util.preference.PerPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naoto on 16/06/25.
 */
public final class APIURLBuilder {

    private static final String ENTRY_POINT = "https://qiita.com/api/v2";
    private static final String PAGE_KEY = "page";
    private static final String PER_PAGE_KEY = "per_page";

    private final Context context;
    private String urlString;
    private List<String> params;

    public APIURLBuilder(Context context) {
        this.context = context;
        this.urlString = ENTRY_POINT;
        this.params = new ArrayList<>();
    }

    public String build() {
        return this.urlString + "?" + this.buildQuery();
    }

    public APIURLBuilder setPath(@NonNull String path) {
        this.urlString += path;
        return this;
    }

    public APIURLBuilder setPage(int page) {
        if (page == 0) {
            return this;
        }

        String query = PAGE_KEY + "=" + page;
        this.params.add(query);
        return this;
    }

    public APIURLBuilder setPerPage() {
        String perPage = PerPage.get();
        String query = PER_PAGE_KEY + "=" + perPage;
        this.params.add(query);
        return this;
    }

    public APIURLBuilder setParams(@NonNull String key, @NonNull String value) {
        if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
            return this;
        }

        String query = key + "=" + value;
        this.params.add(query);
        return this;
    }

    private String buildQuery() {
        String query = null;

        for (String param: this.params) {
            if (TextUtils.isEmpty(query)) {
                query += param;
            } else {
                query = query + "&" + param;
            }
        }

        return query;
    }
}
