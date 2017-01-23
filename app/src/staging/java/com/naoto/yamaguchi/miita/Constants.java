package com.naoto.yamaguchi.miita;

/**
 * BuildType: Staging
 *
 * Created by naoto on 2017/01/23.
 */

public final class Constants {
    public static final boolean DEBUG = true;

    public static class Qiita {
        public static final String ENTRY_POINT = "https://qiita.com/api/v2";
        public static final String CLIENT_ID_KEY = "client_id";
        public static final String CLIENT_ID = BuildConfig.CLIENT_ID;
        public static final String CLIENT_SECRET_KEY = "client_secret";
        public static final String CLIENT_SECRET = BuildConfig.CLIENT_SECRET;
        public static final String CODE_KEY = "code";
        public static final String SCOPE = "read_qiita%20write_qiita";
        public static final String AUTHORIZE_URL =
                ENTRY_POINT + "/oauth/authorize?client_id=" + CLIENT_ID + "&scope=" + SCOPE;

        private Qiita() {}
    }

    private Constants() {}
}
