package com.naoto.yamaguchi.miita.dao.base;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

/**
 * Created by naoto on 16/09/17.
 */
public abstract class BaseItemDao<T extends RealmObject> implements BaseDao {

    protected Realm realm;
    protected Class<T> aClass;

    public BaseItemDao(Class<T> aClass) {
        this.aClass = aClass;
        this.getClient();
    }

    @Override
    public void getClient() {
        this.realm = Realm.getDefaultInstance();
    }

    public List<T> insert(List<T> items) {
        this.realm.beginTransaction();
        List<T> realmItems = this.realm.copyToRealmOrUpdate(items);
        this.realm.commitTransaction();
        return realmItems;
    }

    public RealmResults<T> findAll() {
        return this.realm.where(this.aClass).findAll();
    }

    public void truncate() {
        RealmResults<T> items = this.findAll();
        this.realm.beginTransaction();
        items.deleteAllFromRealm();
        this.realm.commitTransaction();
    }

    @Override
    public void close() {
        this.realm.close();
    }
}
