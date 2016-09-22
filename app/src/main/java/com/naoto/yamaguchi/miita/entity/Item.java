package com.naoto.yamaguchi.miita.entity;

import com.naoto.yamaguchi.miita.entity.base.BaseItem;

/**
 * Created by naoto on 16/07/17.
 */
public class Item implements BaseItem {

    private String id;
    private String title;
    private String body;
    private String urlString;
    private User user;

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
    public User getUser() {
        return this.user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }
}
