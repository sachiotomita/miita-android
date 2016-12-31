package com.naoto.yamaguchi.miita.helper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by naoto on 2016/12/31.
 */

public final class JSONHelper {
    private static JSONHelper instance = null;

    public static synchronized JSONHelper getInstance() {
        if (instance == null) {
            instance = new JSONHelper();
        }
        return instance;
    }

    public String getJSONString(String fileName) throws Exception {
        final InputStream inputStream = this.getClass().getClassLoader()
                .getResourceAsStream(fileName);

        final InputStreamReader streamReader = new InputStreamReader(inputStream);
        final BufferedReader bufferedReader = new BufferedReader(streamReader);
        final StringBuilder stringBuilder = new StringBuilder();
        String input;
        while ((input = bufferedReader.readLine()) != null) {
            stringBuilder.append(input);
        }

        bufferedReader.close();

        return stringBuilder.toString();
    }
}
