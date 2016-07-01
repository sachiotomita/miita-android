package com.naoto.yamaguchi.miita.mapper;

import com.naoto.yamaguchi.miita.entity.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by naoto on 16/06/29.
 */
public final class UserObjectMapper {

    private static final String ID_KEY = "id";
    private static final String IMAGE_KEY = "profile_image_url";

    // TODO: map処理共通化
    public static User map(JSONObject json) throws JSONException {
        try {
            User user = new User();

            String id = json.getString(ID_KEY);
            user.setId(id);

            String imageUrlString = json.getString(IMAGE_KEY);
            user.setImageUrlString(imageUrlString);

            return user;
        } catch (JSONException e) {
            throw e;
        }
    }

    public static User map(String jsonString) throws JSONException {
        try {
            User user = new User();
            JSONObject json = new JSONObject(jsonString);

            String id = json.getString(ID_KEY);
            user.setId(id);

            String imageUrlString = json.getString(IMAGE_KEY);
            user.setImageUrlString(imageUrlString);

            return user;
        } catch (JSONException e) {
            throw e;
        }
    }
}
