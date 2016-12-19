package com.naoto.yamaguchi.miita.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.naoto.yamaguchi.miita.entity.base.BaseUser;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * User Entity.
 * Realm Table.
 * - id
 * - name
 * - description
 * - image url
 * - followees count
 * - followers count
 * <p>
 * Created by naoto on 16/06/25.
 */
public class User extends RealmObject implements BaseUser, Parcelable {

    @PrimaryKey
    private String id;
    private String name;
    private String description;
    private String imageUrlString;
    private int followeesCount;
    private int followersCount;

    public User() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.name);
        parcel.writeString(this.description);
        parcel.writeString(this.imageUrlString);
        parcel.writeInt(this.followeesCount);
        parcel.writeInt(this.followersCount);
    }

    public static final Parcelable.Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel parcel) {
            return new User(parcel);
        }

        @Override
        public User[] newArray(int i) {
            return new User[i];
        }
    };

    private User(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.imageUrlString = in.readString();
        this.followeesCount = in.readInt();
        this.followersCount = in.readInt();
    }
}
