package com.naoto.yamaguchi.miita.entity;

import android.os.Parcel;
import android.os.Parcelable;

import com.naoto.yamaguchi.miita.entity.base.BaseItemTag;

/**
 * Item Tag Entity.
 *
 * Created by naoto on 2016/11/19.
 */

public class ItemTag implements BaseItemTag, Parcelable {

  private String name;

  public ItemTag() {}

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(this.name);
  }

  public static final Parcelable.Creator<ItemTag> CREATOR = new Creator<ItemTag>() {
    @Override
    public ItemTag createFromParcel(Parcel parcel) {
      return new ItemTag(parcel);
    }

    @Override
    public ItemTag[] newArray(int i) {
      return new ItemTag[i];
    }
  };

  private ItemTag(Parcel in) {
    this.name = in.readString();
  }
}
