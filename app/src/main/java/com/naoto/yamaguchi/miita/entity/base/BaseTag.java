package com.naoto.yamaguchi.miita.entity.base;

/**
 * Base Tag Interface.
 *
 * Created by naoto on 16/07/10.
 */
public interface BaseTag {

  String getId();
  void setId(String id);

  String getIconUrlString();
  void setIconUrlString(String iconUrlString);

  int getItemsCount();
  void setItemsCount(int itemsCount);

  int getFollowersCount();
  void setFollowersCount(int followersCount);
}
