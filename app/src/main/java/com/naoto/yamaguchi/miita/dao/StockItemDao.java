package com.naoto.yamaguchi.miita.dao;

import com.naoto.yamaguchi.miita.entity.StockItem;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by naoto on 16/06/30.
 */
public final class StockItemDao {

    private Realm realm;

    public StockItemDao() {
        this.realm = Realm.getDefaultInstance();
    }

    public List<StockItem> insert(List<StockItem> items) {
        this.realm.beginTransaction();
        List<StockItem> realmItems = this.realm.copyToRealmOrUpdate(items);
        this.realm.commitTransaction();
        return realmItems;
    }

    public RealmResults<StockItem> findAll() {
        return this.realm.where(StockItem.class).findAll();
    }

    public void truncate() {
        RealmResults<StockItem> items = this.findAll();
        this.realm.beginTransaction();
        items.deleteAllFromRealm();
        this.realm.commitTransaction();
    }

    public void close() {
        this.realm.close();
    }
}
