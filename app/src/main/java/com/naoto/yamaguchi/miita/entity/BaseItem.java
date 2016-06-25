package com.naoto.yamaguchi.miita.entity;

/**
 * Created by naoto on 16/06/25.
 */
public interface BaseItem {
    String getID();
    void setID(String id);
    String getTitle();
    void setTitle(String title);
    String getBody();
    void setBody(String body);
    BaseUser getUser();
    void setUser(BaseUser user);
}
