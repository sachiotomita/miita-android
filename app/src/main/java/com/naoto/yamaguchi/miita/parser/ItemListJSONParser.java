package com.naoto.yamaguchi.miita.parser;

import com.naoto.yamaguchi.miita.entity.ui.ItemTag;
import com.naoto.yamaguchi.miita.entity.base.BaseItem;
import com.naoto.yamaguchi.miita.entity.api.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * JSON to List<BaseItem>.
 * <p>
 * Created by naoto on 16/06/25.
 */
public final class ItemListJSONParser {

    private static final String ID_KEY = "id";
    private static final String TITLE_KEY = "title";
    private static final String BODY_KEY = "rendered_body";
    private static final String URL_KEY = "url";
    private static final String CREATED_AT_KEY = "created_at";
    private static final String TAG_KEY = "tags";
    private static final String USER_KEY = "user";

    /**
     * usage: ItemListJSONParser.map(jsonArray, AllItem.class);
     *
     * @param jsonString
     * @param aClass
     * @param <T>
     * @return List<T>
     * @throws JSONException, IllegalAccessException, InstantiationException
     */
    // TODO: 引数の順番入れ替える
    public static <T extends BaseItem> List<T> parse(String jsonString, Class<T> aClass)
            throws JSONException, IllegalAccessException, InstantiationException,
            ParseException {
        try {
            final List<T> itemList = new ArrayList<>();
            final JSONArray jsonArray = new JSONArray(jsonString);

            for (int i = 0; i < jsonArray.length(); i++) {
                final T item = aClass.newInstance();
                final JSONObject itemJson = jsonArray.getJSONObject(i);

                final String id = itemJson.getString(ID_KEY);
                item.setId(id);

                final String title = itemJson.getString(TITLE_KEY);
                item.setTitle(title);

                final String body = itemJson.getString(BODY_KEY);
                item.setBody(body);

                final String urlString = itemJson.getString(URL_KEY);
                item.setUrlString(urlString);

                // TODO: check format
                // http://d.hatena.ne.jp/drambuie/20110219/p1
                // - yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
                final String createdAtString = itemJson.getString(CREATED_AT_KEY);
                final SimpleDateFormat df =
                        new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss", Locale.JAPAN);
                final Date createdAt = df.parse(createdAtString);
                item.setCreatedAt(createdAt);

                final JSONArray tagsArray = itemJson.getJSONArray(TAG_KEY);
                final List<ItemTag> tags = ItemTagListJSONParser.parse(ItemTag.class, tagsArray);
                item.setTags(tags);

                final JSONObject userJson = itemJson.getJSONObject(USER_KEY);
                final User user = UserJSONParser.parse(userJson);
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
        } catch (ParseException e) {
            throw e;
        }
    }
}
