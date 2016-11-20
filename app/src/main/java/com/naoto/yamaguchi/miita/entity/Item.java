package com.naoto.yamaguchi.miita.entity;

import com.naoto.yamaguchi.miita.entity.base.BaseItem;

import java.util.Date;
import java.util.List;

/**
 * General Item Entity.
 * Not Realm Object.
 *
 * Created by naoto on 16/07/17.
 */
public class Item implements BaseItem {

  private String id;
  private String title;
  private String body;
  private String urlString;
  private Date createdAt;
  private List<ItemTag> tags;
  private User user;

  public Item() {}

  @Override
  public String getId() {
    return this.id;
  }

  @Override
  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String getTitle() {
    return this.title;
  }

  @Override
  public void setTitle(String title) {
    this.title = title;
  }

  @Override
  public String getBody() {
    return this.body;
  }

  @Override
  public void setBody(String body) {
    this.body = body;
  }

  @Override
  public String getUrlString() {
    return this.urlString;
  }

  @Override
  public void setUrlString(String urlString) {
    this.urlString = urlString;
  }

  @Override
  public Date getCreatedAt() {
    return this.createdAt;
  }

  @Override
  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public List<ItemTag> getTags() {
    return this.tags;
  }

  @Override
  public void setTags(List<ItemTag> tags) {
    this.tags = tags;
  }

  @Override
  public User getUser() {
    return this.user;
  }

  @Override
  public void setUser(User user) {
    this.user = user;
  }
}
