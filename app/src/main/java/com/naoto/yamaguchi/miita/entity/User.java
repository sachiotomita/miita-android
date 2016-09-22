package com.naoto.yamaguchi.miita.entity;

import com.naoto.yamaguchi.miita.entity.base.BaseUser;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by naoto on 16/06/25.
 */
public class User extends RealmObject implements BaseUser {

    @PrimaryKey
    private String id;
    private String name;
    private String description;
    private String imageUrlString;
    private int followeesCount;
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
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getImageUrlString() {
        return this.imageUrlString;
    }

    @Override
    public void setImageUrlString(String imageUrlString) {
        this.imageUrlString = imageUrlString;
    }

    @Override
    public int getFolloweesCount() {
        return this.followeesCount;
    }

    @Override
    public void setFolloweesCount(int followeesCount) {
        this.followeesCount = followeesCount;
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
