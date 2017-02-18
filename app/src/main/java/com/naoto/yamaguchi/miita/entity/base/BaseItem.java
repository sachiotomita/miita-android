package com.naoto.yamaguchi.miita.entity.base;

import com.naoto.yamaguchi.miita.entity.ui.ItemTag;
import com.naoto.yamaguchi.miita.entity.api.User;

import java.util.Date;
import java.util.List;

/**
 * Base Item Interface.
 * <p>
 * Created by naoto on 16/06/25.
 */
public interface BaseItem {

    String getId();

    void setId(String id);

    String getTitle();

    void setTitle(String title);

    String getBody();

    void setBody(String body);

    String getUrlString();

    void setUrlString(String urlString);

    Date getCreatedAt();

    String getCreatedAtString();

    void setCreatedAt(Date createdAt);

    List<ItemTag> getTags();

    void setTags(List<ItemTag> tags);

    User getUser();

    void setUser(User user);
}
