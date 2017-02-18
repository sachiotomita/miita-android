package com.naoto.yamaguchi.miita.parser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON to Token string.
 * <p>
 * Created by naoto on 16/06/30.
 */
public final class AccessTokenObjectMapper {
    private static final String TOKEN_KEY = "token";

    public static String map(String jsonString) throws JSONException {
        try {
            JSONObject json = new JSONObject(jsonString);
            String token = json.getString(TOKEN_KEY);
            return token;
        } catch (JSONException e) {
            throw e;
        }
    }
}
