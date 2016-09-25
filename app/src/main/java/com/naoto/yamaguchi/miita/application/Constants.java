package com.naoto.yamaguchi.miita.application;

/**
 * Created by naoto on 16/07/01.
 */
public final class Constants {
    public static final String ENTRY_POINT = "https://qiita.com/api/v2";
    public static final String CLIENT_ID_KEY = "client_id";
    public static final String CLIENT_ID = "1f9862b42618ce3f030a4988215fb20befba3cfa";
    public static final String CLIENT_SECRET_KEY = "client_secret";
    public static final String CLIENT_SECRET = "c298ca57963361cb4206dc1670e522d979a86b7b";
    public static final String CODE_KEY = "code";
    public static final String SCOPE = "read_qiita%20write_qiita";
    public static final String AUTHORIZE_URL =
            ENTRY_POINT + "/oauth/authorize?client_id=" + CLIENT_ID + "&scope=" + SCOPE;

    private Constants() {}
}
