package com.naoto.yamaguchi.miita.mapper;

import com.naoto.yamaguchi.miita.entity.base.BaseItemTag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON to ItemTag List.
 *
 * Created by naoto on 2016/11/19.
 */

public final class ItemTagListObjectMapper {
  private static final String NAME_KEY = "name";

  public static <T extends BaseItemTag> List<T> map(Class<T> aClass, JSONArray jsonArray)
          throws JSONException, IllegalAccessException, InstantiationException {
    try {
      List<T> tags = new ArrayList<>();

      for (int i = 0; i < jsonArray.length(); i++) {
        T tag = aClass.newInstance();
        JSONObject tagJson = jsonArray.getJSONObject(i);

        String name = tagJson.getString(NAME_KEY);
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
