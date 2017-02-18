package com.naoto.yamaguchi.miita.converter;

import com.naoto.yamaguchi.miita.entity.ui.Tag;
import com.naoto.yamaguchi.miita.entity.base.BaseTag;

/**
 * Convert {@FollowTag} to {@Tag}
 * <p>
 * Created by naoto on 2016/11/23.
 */

public final class TagConverter {
    public static <T extends BaseTag> Tag convert(T fromTag) {
        Tag tag = new Tag();
        tag.setId(fromTag.getId());
        tag.setIconUrlString(tag.getIconUrlString());
        tag.setItemsCount(tag.getItemsCount());
        tag.setFollowersCount(tag.getFollowersCount());
        return tag;
    }
}
