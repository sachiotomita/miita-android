package com.naoto.yamaguchi.miita.entity;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by naoto on 16/06/25.
 */
public class User extends RealmObject implements BaseUser {

    @PrimaryKey
    private String id;
    private String imageUrlString;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageUrlString() {
        return this.imageUrlString;
    }

    public void setImageUrlString(String imageUrlString) {
        this.imageUrlString = imageUrlString;
    }

}
