package com.naoto.yamaguchi.miita.entity;

import com.naoto.yamaguchi.miita.entity.base.BaseItemTag;

import io.realm.RealmObject;

/**
 * Item Tag Entity.
 * Realm Table.
 * - name
 *
 * Created by naoto on 2016/11/19.
 */

public class ItemTag extends RealmObject implements BaseItemTag {

  private String name;

  public ItemTag() {
    // for Realm
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }
}
