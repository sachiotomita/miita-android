package com.naoto.yamaguchi.miita.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.naoto.yamaguchi.miita.entity.base.BaseTag;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Follow Tag Entity.
 * Realm Table.
 * - id
 * - icon url
 * - item count
 * - followers count
 *
 * Created by naoto on 16/07/10.
 */
public class FollowTag extends RealmObject implements BaseTag, Parcelable {

  @PrimaryKey
  private String id;
  private String iconUrlString;
  private int itemsCount;
  private int followersCount;

  public FollowTag() {}

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
    parcel.writeString(this.iconUrlString);
    parcel.writeInt(this.itemsCount);
    parcel.writeInt(this.followersCount);
  }

  public static final Parcelable.Creator<FollowTag> CREATOR = new Creator<FollowTag>() {
    @Override
    public FollowTag createFromParcel(Parcel parcel) {
      return new FollowTag(parcel);
    }

    @Override
    public FollowTag[] newArray(int i) {
      return new FollowTag[i];
    }
  };

  private FollowTag(Parcel in) {
    this.id = in.readString();
    this.iconUrlString = in.readString();
    this.itemsCount = in.readInt();
    this.followersCount = in.readInt();
  }
}
