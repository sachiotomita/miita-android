package com.naoto.yamaguchi.miita.entity.base;

import com.naoto.yamaguchi.miita.entity.ItemTag;
import com.naoto.yamaguchi.miita.entity.User;

import java.util.List;

/**
 * Base Item Interface.
 *
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

  String getCreatedAt();
  void setCreatedAt(String createdAt);

  List<ItemTag> getTags();
  void setTags(List<ItemTag> tags);

  User getUser();
  void setUser(User user);
}
