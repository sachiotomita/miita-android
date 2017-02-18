package com.naoto.yamaguchi.miita.parser;

import com.naoto.yamaguchi.miita.entity.base.BaseItemTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON to ItemTag List.
 * <p>
 * Created by naoto on 2016/11/19.
 */

public final class ItemTagListJSONParser {
    private static final String NAME_KEY = "name";

    public static <T extends BaseItemTag> List<T> parse(Class<T> aClass, JSONArray jsonArray)
            throws JSONException, IllegalAccessException, InstantiationException {
        try {
            final List<T> tags = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                final T tag = aClass.newInstance();
                final JSONObject tagJson = jsonArray.getJSONObject(i);

                final String name = tagJson.getString(NAME_KEY);
                tag.setName(name);

                tags.add(tag);
            }

            return tags;
        } catch (JSONException e) {
            return null;
        } catch (IllegalAccessException e) {
            return null;
        } catch (InstantiationException e) {
            return null;
        }
    }
}
