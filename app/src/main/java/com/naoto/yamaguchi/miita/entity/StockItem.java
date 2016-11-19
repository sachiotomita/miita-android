package com.naoto.yamaguchi.miita.entity;

import com.naoto.yamaguchi.miita.entity.base.BaseItem;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Stock Item Entity.
 * Realm Table.
 * - id
 * - title
 * - body
 * - item url
 * - created at
 * - tags
 * - User
 *
 * Created by naoto on 16/06/25.
 */
public class StockItem extends RealmObject implements BaseItem {

  @PrimaryKey
  private String id;
  private String title;
  private String body;
  private String urlString;
  private String createdAt;
  private List<Tag> tags;
  private User user;

  public StockItem() {
    // for Realm
  }

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
  public String getCreatedAt() {
    return this.createdAt;
  }

  @Override
  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }

  @Override
  public List<Tag> getTags() {
    return this.tags;
  }

  @Override
  public void setTags(List<Tag> tags) {
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
