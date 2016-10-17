package com.naoto.yamaguchi.miita.mapper;

import com.naoto.yamaguchi.miita.entity.base.BaseItem;
import com.naoto.yamaguchi.miita.entity.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON to List<BaseItem>.
 *
 * Created by naoto on 16/06/25.
 */
public final class ItemListObjectMapper {

  private static final String ID_KEY = "id";
  private static final String TITLE_KEY = "title";
  private static final String BODY_KEY = "rendered_body";
  private static final String URL_KEY = "url";
  private static final String USER_KEY = "user";

  /**
   * usage: ItemListObjectMapper.map(jsonArray, AllItem.class);
   *
   * @param jsonString
   * @param aClass
   * @param <T>
   * @return List<T>
   * @throws JSONException, IllegalAccessException, InstantiationException
   */
  // TODO: 引数の順番入れ替える
  public static <T extends BaseItem> List<T> map(String jsonString, Class<T> aClass)
          throws JSONException, IllegalAccessException, InstantiationException {
    try {
      List<T> itemList = new ArrayList<>();
      JSONArray jsonArray = new JSONArray(jsonString);

      for (int i = 0; i < jsonArray.length(); i++) {
        T item = aClass.newInstance();
        JSONObject itemJson = jsonArray.getJSONObject(i);

        String id = itemJson.getString(ID_KEY);
        item.setId(id);

        String title = itemJson.getString(TITLE_KEY);
        item.setTitle(title);

        String body = itemJson.getString(BODY_KEY);
        item.setBody(body);

        String urlString = itemJson.getString(URL_KEY);
        item.setUrlString(urlString);

        JSONObject userJson = itemJson.getJSONObject(USER_KEY);
        User user = UserObjectMapper.map(userJson);
        item.setUser(user);

        itemList.add(item);
      }

      return itemList;

    } catch (JSONException e) {
      throw e;
    } catch (IllegalAccessException e) {
      throw e;
    } catch (InstantiationException e) {
      throw e;
    }
  }
}
