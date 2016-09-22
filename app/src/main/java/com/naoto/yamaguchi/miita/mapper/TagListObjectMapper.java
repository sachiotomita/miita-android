package com.naoto.yamaguchi.miita.mapper;

import com.naoto.yamaguchi.miita.api.APIException;
import com.naoto.yamaguchi.miita.entity.base.BaseTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by naoto on 16/07/12.
 */
public final class TagListObjectMapper {

    private static final String ID_KEY = "id";
    private static final String ICON_KEY = "icon_url";
    private static final String ITEM_COUNT_KEY = "items_count";
    private static final String FOLLOWERS_COUNT_KEY = "followers_count";

    public static <T extends BaseTag> List<T> map(Class<T> aClass, String jsonString)
            throws APIException {
        try {
            List<T> tagList = new ArrayList<>();
            JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                T tag = aClass.newInstance();
                JSONObject tagJson = jsonArray.getJSONObject(i);

                String id = tagJson.getString(ID_KEY);
                tag.setId(id);

                String iconUrlString = tagJson.getString(ICON_KEY);
                tag.setIconUrlString(iconUrlString);

                int itemsCount = tagJson.getInt(ITEM_COUNT_KEY);
                tag.setItemsCount(itemsCount);

                int followersCount = tagJson.getInt(FOLLOWERS_COUNT_KEY);
                tag.setFollowersCount(followersCount);
            }

            return tagList;
            
        } catch (JSONException e) {
            throw new APIException(e.toString());
        } catch (IllegalAccessException e) {
            throw new APIException(e.toString());
        } catch (InstantiationException e) {
            throw new APIException(e.toString());
        }
    }
}
