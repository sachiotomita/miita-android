package com.naoto.yamaguchi.miita.parser;

import com.naoto.yamaguchi.miita.entity.api.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON to User.
 * <p>
 * Created by naoto on 16/06/29.
 */
public final class UserJSONParser {

    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";
    private static final String DESCRIPTION_KEY = "description";
    private static final String IMAGE_KEY = "profile_image_url";
    private static final String FOLLOWEES_COUNT_KEY = "followees_count";
    private static final String FOLLOWERS_COUNT_KEY = "followers_count";

    public static User parse(JSONObject json) throws JSONException {
        try {
            return p_parse(json);
        } catch (JSONException e) {
            throw e;
        }
    }

    public static User parse(String jsonString) throws JSONException {
        try {
            final JSONObject json = new JSONObject(jsonString);
            return p_parse(json);
        } catch (JSONException e) {
            throw e;
        }
    }

    private static User p_parse(JSONObject json) throws JSONException {
        try {
            final User user = new User();

            final String id = json.getString(ID_KEY);
            user.setId(id);

            final String name = json.getString(NAME_KEY);
            user.setName(name);

            final String description = json.getString(DESCRIPTION_KEY);
            user.setDescription(description);

            final String imageUrlString = json.getString(IMAGE_KEY);
            user.setImageUrlString(imageUrlString);

            final int followeesCount = json.getInt(FOLLOWEES_COUNT_KEY);
            user.setFolloweesCount(followeesCount);

            final int followersCount = json.getInt(FOLLOWERS_COUNT_KEY);
            user.setFollowersCount(followersCount);

            return user;
        } catch (JSONException e) {
            throw e;
        }
    }
}
