package com.naoto.yamaguchi.miita.mapper;

import com.naoto.yamaguchi.miita.entity.api.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON to User.
 * <p>
 * Created by naoto on 16/06/29.
 */
public final class UserObjectMapper {

    private static final String ID_KEY = "id";
    private static final String NAME_KEY = "name";
    private static final String DESCRIPTION_KEY = "description";
    private static final String IMAGE_KEY = "profile_image_url";
    private static final String FOLLOWEES_COUNT_KEY = "followees_count";
    private static final String FOLLOWERS_COUNT_KEY = "followers_count";

    public static User map(JSONObject json) throws JSONException {
        try {
            User user = parse(json);
            return user;
        } catch (JSONException e) {
            throw e;
        }
    }

    public static User map(String jsonString) throws JSONException {
        try {
            JSONObject json = new JSONObject(jsonString);
            User user = parse(json);
            return user;
        } catch (JSONException e) {
            throw e;
        }
    }

    private static User parse(JSONObject json) throws JSONException {
        try {
            User user = new User();

            String id = json.getString(ID_KEY);
            user.setId(id);

            String name = json.getString(NAME_KEY);
            user.setName(name);

            String description = json.getString(DESCRIPTION_KEY);
            user.setDescription(description);

            String imageUrlString = json.getString(IMAGE_KEY);
            user.setImageUrlString(imageUrlString);

            int followeesCount = json.getInt(FOLLOWEES_COUNT_KEY);
            user.setFolloweesCount(followeesCount);

            int followersCount = json.getInt(FOLLOWERS_COUNT_KEY);
            user.setFollowersCount(followersCount);

            return user;
        } catch (JSONException e) {
            throw e;
        }
    }
}
