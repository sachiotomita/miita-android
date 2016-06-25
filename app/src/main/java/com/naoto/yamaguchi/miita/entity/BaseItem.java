package com.naoto.yamaguchi.miita.entity;

/**
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
    User getUser();
    void setUser(User user);
}
