package com.naoto.yamaguchi.miita.converter;

import com.naoto.yamaguchi.miita.entity.ui.Item;
import com.naoto.yamaguchi.miita.entity.base.BaseItem;

/**
 * Convert {@AllItem}, {@StockItem}, {@TagItem} to {@Item}
 * <p>
 * Created by naoto on 2016/11/21.
 */

// TODO: create API Data Interface.
public final class ItemConverter {
    public static <T extends BaseItem> Item convert(T fromItem) {
        Item item = new Item();
        item.setId(fromItem.getId());
        item.setTitle(fromItem.getTitle());
        item.setBody(fromItem.getBody());
        item.setUrlString(fromItem.getUrlString());
        item.setCreatedAt(fromItem.getCreatedAt());
        item.setTags(fromItem.getTags());
        item.setUser(fromItem.getUser());
        return item;
    }
}
