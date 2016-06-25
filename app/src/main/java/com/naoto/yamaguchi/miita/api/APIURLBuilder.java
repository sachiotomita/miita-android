package com.naoto.yamaguchi.miita.api;

/**
 * Created by naoto on 16/06/25.
 */
public final class APIURLBuilder {

    private static final String ENTRY_POINT = "https://qiita.com/api/v2";
    private static final String PER_PAGE = "10";

    public static String build(String path, int page) {
        String urlString = ENTRY_POINT + path;
        urlString += getPageQuery(page);
        urlString += getPerPageQuery();
        return urlString;
    }

    public static String build(String path) {
        String urlString = ENTRY_POINT + path;
        return urlString;
    }

    private static String getPageQuery(int page) {
        String query = "?page=" + page;
        return query;
    }

    private static String getPerPageQuery() {
        String query = "&per_page=" + PER_PAGE;
        return query;
    }
}
