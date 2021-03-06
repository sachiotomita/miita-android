package com.naoto.yamaguchi.miita.entity;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.naoto.yamaguchi.miita.entity.base.BaseItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Stock Item Entity.
 * Realm Table.
 * - id
 * - title
 * - body
 * - item url
 * - created at
 * - tagsString
 * - User
 * <p>
 * Created by naoto on 16/06/25.
 */
public class StockItem extends RealmObject implements BaseItem, Parcelable {

    @PrimaryKey
    private String id;
    private String title;
    private String body;
    private String urlString;
    private Date createdAt;
    private String tagsString;
    private User user;

    public StockItem() {
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
    public Date getCreatedAt() {
        return this.createdAt;
    }

    @Override
    public String getCreatedAtString() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.createdAt);
        return cal.get(Calendar.YEAR) + "年"
                + (cal.get(Calendar.MONTH) + 1) + "月"
                + cal.get(Calendar.DAY_OF_MONTH) + "日";
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public List<ItemTag> getTags() {
        List<ItemTag> tags = new ArrayList<>();
        List<String> tagNameList = Arrays.asList(this.tagsString.split(","));
        for (String name : tagNameList) {
            ItemTag tag = new ItemTag();
            tag.setName(name);
            tags.add(tag);
        }

        return tags;
    }

    @Override
    public void setTags(List<ItemTag> tags) {
        List<String> tagList = new ArrayList<>();
        for (ItemTag tag : tags) {
            tagList.add(tag.getName());
        }

        this.tagsString = TextUtils.join(",", tagList);
    }

    @Override
    public User getUser() {
        return this.user;
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.title);
        parcel.writeString(this.body);
        parcel.writeString(this.urlString);
        parcel.writeValue(this.createdAt);
        parcel.writeString(this.tagsString);
        parcel.writeParcelable(this.user, i);
    }

    public static final Parcelable.Creator<StockItem> CREATOR = new Creator<StockItem>() {
        @Override
        public StockItem createFromParcel(Parcel parcel) {
            return new StockItem(parcel);
        }

        @Override
        public StockItem[] newArray(int i) {
            return new StockItem[i];
        }
    };

    private StockItem(Parcel in) {
        this.id = in.readString();
        this.title = in.readString();
        this.body = in.readString();
        this.urlString = in.readString();
        this.createdAt = (Date) in.readValue(Date.class.getClassLoader());
        this.tagsString = in.readString();
        this.user = (User) in.readParcelable(User.class.getClassLoader());
    }
}
