package com.naoto.yamaguchi.miita.util.intent;

import android.content.Intent;

import com.naoto.yamaguchi.miita.entity.Item;
import com.naoto.yamaguchi.miita.entity.Tag;
import com.naoto.yamaguchi.miita.entity.base.BaseItem;
import com.naoto.yamaguchi.miita.entity.base.BaseTag;

/**
 * Intent Helper.
 *
 * Created by naoto on 2016/11/06.
 */

public final class IntentHandler {
  /**
   * {@link BaseItem} variable key.
   */
  private static final String ITEM_ID_KEY = "item_id";
  private static final String ITEM_TITLE_KEY = "item_title";
  private static final String ITEM_URL_KEY = "item_url";
  private static final String ITEM_BODY_KEY = "item_body";

  /**
   * {@link BaseTag} variable key.
   */
  private static final String TAG_ID_KEY = "tag_id";
  private static final String TAG_ICON_URL_KEY = "tag_icon_url";
  private static final String TAG_ITEM_COUNT_KEY = "tag_item_count";
  private static final String TAG_FOLLOWERS_COUNT_KEY = "tag_followers_count";

  private static final int DEFAULT_INT_VALUE = 0;

  // TODO: すべてのparameterをcopyする
  public static <T extends BaseItem> Intent putItem(Intent intent, T item) {
    intent.putExtra(ITEM_ID_KEY, item.getId());
    intent.putExtra(ITEM_TITLE_KEY, item.getTitle());
    intent.putExtra(ITEM_URL_KEY, item.getUrlString());
    intent.putExtra(ITEM_BODY_KEY, item.getBody());
    return intent;
  }

  // TODO: すべてのparameterをcopyする
  public static <T extends BaseTag> Intent putTag(Intent intent, T tag) {
    intent.putExtra(TAG_ID_KEY, tag.getId());
    intent.putExtra(TAG_ICON_URL_KEY, tag.getIconUrlString());
    intent.putExtra(TAG_ITEM_COUNT_KEY, tag.getItemsCount());
    intent.putExtra(TAG_FOLLOWERS_COUNT_KEY, tag.getFollowersCount());
    return intent;
  }

  public static Item getItem(Intent intent) {
    Item item = new Item();

    String id = intent.getStringExtra(ITEM_ID_KEY);
    String title = intent.getStringExtra(ITEM_TITLE_KEY);
    String urlString = intent.getStringExtra(ITEM_URL_KEY);
    String body = intent.getStringExtra(ITEM_BODY_KEY);

    item.setId(id);
    item.setTitle(title);
    item.setUrlString(urlString);
    item.setBody(body);

    return item;
  }

  public static Tag getTag(Intent intent) {
    Tag tag = new Tag();

    String id = intent.getStringExtra(TAG_ID_KEY);
    String iconUrlString = intent.getStringExtra(TAG_ICON_URL_KEY);
    int itemCount = intent.getIntExtra(TAG_ITEM_COUNT_KEY, DEFAULT_INT_VALUE);
    int followersCount = intent.getIntExtra(TAG_FOLLOWERS_COUNT_KEY, DEFAULT_INT_VALUE);

    tag.setId(id);
    tag.setIconUrlString(iconUrlString);
    tag.setItemsCount(itemCount);
    tag.setFollowersCount(followersCount);

    return tag;
  }
}
