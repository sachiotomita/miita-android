package com.naoto.yamaguchi.miita.parser;

import com.naoto.yamaguchi.miita.entity.base.BaseTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON to List<BaseTag>.
 * <p>
 * Created by naoto on 16/07/12.
 */
public final class TagListJSONParser {

    private static final String ID_KEY = "id";
    private static final String ICON_KEY = "icon_url";
    private static final String ITEM_COUNT_KEY = "items_count";
    private static final String FOLLOWERS_COUNT_KEY = "followers_count";

    public static <T extends BaseTag> List<T> parse(Class<T> aClass, String jsonString)
            throws JSONException, IllegalAccessException, InstantiationException {
        try {
            final List<T> tagList = new ArrayList<>();
            final JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                final T tag = aClass.newInstance();
                final JSONObject tagJson = jsonArray.getJSONObject(i);

                final String id = tagJson.getString(ID_KEY);
                tag.setId(id);

                final String iconUrlString = tagJson.getString(ICON_KEY);
                tag.setIconUrlString(iconUrlString);

                final int itemsCount = tagJson.getInt(ITEM_COUNT_KEY);
                tag.setItemsCount(itemsCount);

                final int followersCount = tagJson.getInt(FOLLOWERS_COUNT_KEY);
                tag.setFollowersCount(followersCount);

                tagList.add(tag);
            }

            return tagList;

        } catch (JSONException e) {
            throw e;
        } catch (IllegalAccessException e) {
            throw e;
        } catch (InstantiationException e) {
            throw e;
        }
    }
}
