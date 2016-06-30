package com.naoto.yamaguchi.miita.mapper;

import com.naoto.yamaguchi.miita.api.APIException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by naoto on 16/06/30.
 */
public final class AccessTokenObjectMapper {

    private static final String TOKEN_KEY = "token";

    public static String map(String jsonString) throws APIException {
        try {
            JSONObject json = new JSONObject(jsonString);
            String token = json.getString(TOKEN_KEY);
            return token;
        } catch (JSONException e) {
            throw new APIException(e.toString());
        }
    }

}
