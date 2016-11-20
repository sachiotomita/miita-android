package com.naoto.yamaguchi.miita.entity;

import com.naoto.yamaguchi.miita.entity.base.BaseItemTag;

/**
 * Item Tag Entity.
 *
 * Created by naoto on 2016/11/19.
 */

public class ItemTag implements BaseItemTag {

  private String name;

  public ItemTag() {}

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }
}
