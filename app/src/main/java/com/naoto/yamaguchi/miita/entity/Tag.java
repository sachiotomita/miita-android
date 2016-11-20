package com.naoto.yamaguchi.miita.entity;

import com.naoto.yamaguchi.miita.entity.base.BaseTag;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * generic Tag entity.
 *
 * Created by naoto on 2016/11/08.
 */

public final class Tag extends RealmObject implements BaseTag {

  @PrimaryKey
  private String id;
  private String iconUrlString;
  private int itemsCount;
  private int followersCount;

  public Tag() {}

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getIconUrlString() {
    return this.iconUrlString;
  }

  @Override
  public void setIconUrlString(String iconUrlString) {
    this.iconUrlString = iconUrlString;
  }

  @Override
  public int getItemsCount() {
    return this.itemsCount;
  }

  @Override
  public void setItemsCount(int itemsCount) {
    this.itemsCount = itemsCount;
  }

  @Override
  public int getFollowersCount() {
    return followersCount;
  }

  @Override
  public void setFollowersCount(int followersCount) {
    this.followersCount = followersCount;
  }
}
