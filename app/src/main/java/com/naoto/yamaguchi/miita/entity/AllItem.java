package com.naoto.yamaguchi.miita.entity;

import com.naoto.yamaguchi.miita.entity.base.BaseItem;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by naoto on 16/06/25.
 */
public class AllItem extends RealmObject implements BaseItem {

    @PrimaryKey
    private String id;
    private String title;
    private String body;
    private String urlString;
    private User user;

    public AllItem() {}

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getUrlString() {
        return this.urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
