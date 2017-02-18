package com.naoto.yamaguchi.miita.service;

import com.naoto.yamaguchi.miita.Constants;
import com.naoto.yamaguchi.miita.api.HttpException;
import com.naoto.yamaguchi.miita.api.Method;
import com.naoto.yamaguchi.miita.api.RequestType;
import com.naoto.yamaguchi.miita.parser.AccessTokenJSONParser;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

/**
 * Request AccessToken.
 * POST: v2/access_tokens
 * <p>
 * Created by naoto on 16/06/30.
 */
public final class AuthorizeService implements RequestType<String> {

    private String code;

    public AuthorizeService() {}

    public AuthorizeService setCode(String code) {
        this.code = code;
        return this;
    }

    @Override
    public Method getMethod() {
        return Method.POST;
    }

    @Override
    public String getPath() {
        return "/access_tokens";
    }

    @Override
    public Map<String, String> getParameters() {
        return new HashMap<String, String>() {
            {
                put(Constants.Qiita.CLIENT_ID_KEY, Constants.Qiita.CLIENT_ID);
                put(Constants.Qiita.CLIENT_SECRET_KEY, Constants.Qiita.CLIENT_SECRET);
                put(Constants.Qiita.CODE_KEY, code);
            }
        };
    }

    @Override
    public String processResponse(String response) throws HttpException {
        try {
            return AccessTokenJSONParser.parse(response);
        } catch (JSONException e) {
            return null;
        }
    }
}
