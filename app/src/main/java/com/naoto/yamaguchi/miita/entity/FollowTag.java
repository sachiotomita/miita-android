package com.naoto.yamaguchi.miita.entity;

import com.naoto.yamaguchi.miita.entity.base.BaseTag;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Follow Tag Entity.
 * Realm Table.
 * - id
 * - icon url
 * - item count
 * - followers count
 *
 * Created by naoto on 16/07/10.
 */
public class FollowTag extends RealmObject implements BaseTag {

  @PrimaryKey
  private String id;
  private String iconUrlString;
  private int itemsCount;
  private int followersCount;

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
    return this.followersCount;
  }

  @Override
  public void setFollowersCount(int followersCount) {
    this.followersCount = followersCount;
  }
}
