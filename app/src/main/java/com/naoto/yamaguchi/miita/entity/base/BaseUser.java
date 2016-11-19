package com.naoto.yamaguchi.miita.entity.base;

/**
 * Base User Interface.
 *
 * Created by naoto on 16/06/25.
 */
public interface BaseUser {

  String getId();
  void setId(String id);

  String getName();
  void setName(String name);

  String getDescription();
  void setDescription(String description);

  String getImageUrlString();
  void setImageUrlString(String imageUrlString);

  int getFolloweesCount();
  void setFolloweesCount(int count);

  int getFollowersCount();
  void setFollowersCount(int count);
}
