package com.naoto.yamaguchi.miita.dao;

import com.naoto.yamaguchi.miita.entity.FollowTag;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by naoto on 16/07/12.
 */
public final class FollowTagDao {

    private Realm realm;

    public FollowTagDao() {
        this.realm = Realm.getDefaultInstance();
    }

    public List<FollowTag> insert(List<FollowTag> items) {
        this.realm.beginTransaction();
        List<FollowTag> realmItems = this.realm.copyToRealmOrUpdate(items);
        this.realm.commitTransaction();
        return realmItems;
    }

    public RealmResults<FollowTag> findAll() {
        return this.realm.where(FollowTag.class).findAll();
    }

    public void truncate() {
        RealmResults<FollowTag> items = this.findAll();
        this.realm.beginTransaction();
        items.deleteAllFromRealm();
        this.realm.commitTransaction();
    }

    public void close() {
        this.realm.close();
    }
}
