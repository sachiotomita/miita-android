package com.naoto.yamaguchi.miita.entity.ui;

import android.os.Parcel;
import android.os.Parcelable;

import com.naoto.yamaguchi.miita.entity.base.BaseTag;

/**
 * generic Tag entity.
 * <p>
 * Created by naoto on 2016/11/08.
 */

public final class Tag implements BaseTag, Parcelable {

    private String id;
    private String iconUrlString;
    private int itemsCount;
    private int followersCount;

    public Tag() {
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
    public String getIconUrlString() {
        return this.iconUrlString;
    }

    @Override
    public void setIconUrlString(String iconUrlString) {
        this.iconUrlString = iconUrlString;
    }

    @Override
    public int getItemsCount() {
        return this.itemsCount;
    }

    @Override
    public void setItemsCount(int itemsCount) {
        this.itemsCount = itemsCount;
    }

    @Override
    public int getFollowersCount() {
        return followersCount;
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
        parcel.writeString(this.iconUrlString);
        parcel.writeInt(this.itemsCount);
        parcel.writeInt(this.followersCount);
    }

    public static final Parcelable.Creator<Tag> CREATOR = new Creator<Tag>() {
        @Override
        public Tag createFromParcel(Parcel parcel) {
            return new Tag(parcel);
        }

        @Override
        public Tag[] newArray(int i) {
            return new Tag[i];
        }
    };

    private Tag(Parcel in) {
        this.id = in.readString();
        this.iconUrlString = in.readString();
        this.itemsCount = in.readInt();
        this.followersCount = in.readInt();
    }
}
