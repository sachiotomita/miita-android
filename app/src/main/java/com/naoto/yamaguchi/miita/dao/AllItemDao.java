package com.naoto.yamaguchi.miita.dao;

import com.naoto.yamaguchi.miita.entity.AllItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by naoto on 16/06/29.
 */
public final class AllItemDao {

    private Realm realm;

    public AllItemDao() {
        this.realm = Realm.getDefaultInstance();
    }

    public List<AllItem> insert(List<AllItem> items) {
        this.realm.beginTransaction();
        List<AllItem> realmItems = this.realm.copyToRealmOrUpdate(items);
        this.realm.commitTransaction();
        return realmItems;
    }

    // TODO: 日付ソート or id ソート
    public RealmResults<AllItem> findAll() {
        return this.realm.where(AllItem.class).findAll();
    }

    public void truncate() {
        RealmResults<AllItem> items = this.findAll();
        this.realm.beginTransaction();
        items.deleteAllFromRealm();
        this.realm.commitTransaction();
    }

    public void close() {
        this.realm.close();
    }
}
